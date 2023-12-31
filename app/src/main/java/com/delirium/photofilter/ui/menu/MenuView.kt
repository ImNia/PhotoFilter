package com.delirium.photofilter.ui.menu

import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.camera.core.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.TextButton
import com.delirium.photofilter.R
import com.delirium.photofilter.ui.theme.BackgroundButtonColor
import com.delirium.photofilter.ui.theme.BackgroundScreenColor
import com.delirium.photofilter.ui.theme.TextOnButtonColor

@Composable
fun MenuButton(view: MainActivity) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundScreenColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = { view.openCameraScreen() },
            contentPadding = PaddingValues(20.dp),
            shape = MaterialTheme.shapes.extraLarge,
            colors = ButtonDefaults.buttonColors(
                containerColor = BackgroundButtonColor,
            ),
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.make_photo),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = TextOnButtonColor,

            )
        }

        Button(
            onClick = { view.getPhotoFromStorage() },
            contentPadding = PaddingValues(20.dp),
            shape = MaterialTheme.shapes.extraLarge,
            colors = ButtonDefaults.buttonColors(
                containerColor = BackgroundButtonColor,
            ),
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.take_photo_storage),
                lineHeight = 50.sp,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = TextOnButtonColor,
            )
        }
    }
}

@Composable
fun CameraScreen(view: MainActivity, imageCapture: ImageCapture?) {
    Column(
        modifier = Modifier.fillMaxSize().background(BackgroundScreenColor)
    ) {
        CameraPreview(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f),
            imageCapture = imageCapture
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = { view.menuScreen() },
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                Text(text = "Close screen")
            }
            TextButton(
                onClick = { view.takePhoto() },
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                Text(text = "Make And Save Photo")
            }
        }
    }
}

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    scaleType: PreviewView.ScaleType = PreviewView.ScaleType.FILL_CENTER,
    imageCapture: ImageCapture?
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    AndroidView(
        modifier = modifier,
        factory = { context ->
            val previewView = PreviewView(context).apply {
                this.scaleType = scaleType
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            }

            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()

                val preview = Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                try {
                    cameraProvider.unbindAll()

                    cameraProvider.bindToLifecycle(
                        lifecycleOwner, cameraSelector, preview, imageCapture
                    )
                } catch (exc: Exception) {
                    Log.e("TAG", "Use case binding failed", exc)
                }
            }, ContextCompat.getMainExecutor(context))

            previewView
        }
    )
}