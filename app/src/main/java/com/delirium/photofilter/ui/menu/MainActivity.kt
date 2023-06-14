package com.delirium.photofilter.ui.menu

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.delirium.photofilter.Creator
import com.delirium.photofilter.EditorPhoto
import com.delirium.photofilter.MainMenu
import com.delirium.photofilter.ui.theme.PhotoFilterTheme

class MainActivity : ComponentActivity(), MainMenu {
    private var imageCapture: ImageCapture? = null
    private val presenter = Creator.provideMenuPresentation(this, this)

    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        )
        { permissions ->
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in REQUIRED_PERMISSIONS && !it.value)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoFilterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MenuButton(this)
                }
            }
        }
    }

    fun openCameraScreen() {
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            requestPermissions()
        }
    }

    private fun startCamera() {
        imageCapture = ImageCapture.Builder().build()
        setContent {
            CameraScreen(this, imageCapture)
        }
    }

    fun takePhoto() {
        presenter.savePhotoInStorage(imageCapture, this)
    }

    override fun savePhoto(imageUri: Uri) {
        val intent = Intent(this, EditorPhoto::class.java)
        intent.putExtra("URI_IMAGE", imageUri.toString())
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

    fun getPhotoFromStorage() {
        presenter.openStorage()
    }

    override fun getPhotoFromStorage(imageUri: Uri) {
        Log.d(TAG, "$imageUri")
        val intent = Intent(this, EditorPhoto::class.java)
        intent.putExtra("URI_IMAGE", imageUri.toString())
        startActivity(intent)
    }

    companion object {
        private const val TAG = "CameraXApp"
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