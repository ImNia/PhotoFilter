package com.delirium.photofilter

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.delirium.photofilter.ui.menu.CameraScreen
import com.delirium.photofilter.ui.menu.MenuButton
import com.delirium.photofilter.ui.menu.MenuViewModel
import com.delirium.photofilter.ui.theme.PhotoFilterTheme
import java.text.SimpleDateFormat
import java.util.Locale


class MainActivity : ComponentActivity(), Menu {
    private var imageCapture: ImageCapture? = null
    private val viewModel = MenuViewModel(this)
    private var linkPhoto: Uri? = null

    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        )
        { permissions ->
            // Handle Permission granted/rejected
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in REQUIRED_PERMISSIONS && it.value == false)
                    permissionGranted = false
            }
            if (!permissionGranted) {
                Toast.makeText(
                    baseContext,
                    "Permission request denied",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                startCamera()
            }
        }

    private val activityResultForPhotoFromStorage =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            Log.d(TAG, "In val")
//            if (result. == RESULT_OK) {
                if (uri != null) {
//                    Log.d(TAG, "${intent.data}")
                    val imageUri = uri
//                val imageStream = getContentResolver().openInputStream(imageUri)
//                val selectedImage = BitmapFactory.decodeStream(imageStream);
                    openEditorPhoto(imageUri)
                } else {
                    Log.d(TAG, "Problem!!")
                }
//            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoFilterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MenuButton(viewModel)
                }
            }
        }
    }

    override fun openCameraScreen() {
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            requestPermissions()
        }
    }

    private fun startCamera() {
        imageCapture = ImageCapture.Builder().build()
        setContent {
            CameraScreen(viewModel, imageCapture)
        }
    }

    override fun takePhoto() {
        if (imageCapture == null) {
            val msg = "Can't take a photo, please restart app"
            Toast.makeText(baseContext, msg, Toast.LENGTH_LONG).show()
            return
        }

        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            .build()

        imageCapture!!.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                    linkPhoto = null
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val msg = "Photo capture succeeded: ${output.savedUri}"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                    linkPhoto = output.savedUri
                }
            }
        )
    }

    override fun savePhoto() {
        val intent = Intent(this, EditorPhoto::class.java)
        intent.putExtra("URI_IMAGE", linkPhoto.toString())
        startActivity(intent)
    }

    private fun openEditorPhoto(uri: Uri) {
        Log.d(TAG, "$uri")
        val intent = Intent(this, EditorPhoto::class.java)
        intent.putExtra("URI_IMAGE", uri.toString())
        startActivity(intent)
    }
    private fun requestPermissions() {
        activityResultLauncher.launch(REQUIRED_PERMISSIONS)
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }


    override fun getPhotoFromStorage() {
        activityResultForPhotoFromStorage.launch(arrayOf("image/*"))
    }

    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}