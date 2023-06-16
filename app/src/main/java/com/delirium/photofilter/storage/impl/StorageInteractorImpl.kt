package com.delirium.photofilter.storage.impl

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.core.content.ContextCompat
import com.delirium.photofilter.storage.api.StorageInteractor
import java.text.SimpleDateFormat
import java.util.Locale


class StorageInteractorImpl(activity: ComponentActivity) : StorageInteractor {
    private var consumer: StorageInteractor.StorageConsumer? = null
    private val activityResultForPhotoFromStorage =
        activity.registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            if (uri != null) {
                consumer?.consume(uri)
            } else {
                Log.d(TAG, "Problem!!")
            }
        }

    override fun getPhoto(consumer: StorageInteractor.StorageConsumer) {
        if (this.consumer == null) this.consumer = consumer
        activityResultForPhotoFromStorage.launch(arrayOf("image/*"))
    }

    override fun savePhotoFromCamera(
        imageCapture: ImageCapture?,
        context: Context,
        consumer: StorageInteractor.StorageConsumer
    ) {
        if (imageCapture == null) {
            val msg = "Can't take a photo, please restart app"
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
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
                context.contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            .build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val msg = "Photo capture succeeded: ${output.savedUri}"
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                    output.savedUri?.let {
                        consumer.consume(it)
                    }
                }
            }
        )
    }

    override fun savePhoto(context: Context, bitmap: Bitmap) {
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        val resolver = context.contentResolver
        val uri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val msg = if (uri != null) {
            "Photo save succeeded: $uri"
        } else {
            "Photo save failed. Try again."
        }
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }
}