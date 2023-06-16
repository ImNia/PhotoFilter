package com.delirium.photofilter.storage.api

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.camera.core.ImageCapture

interface StorageInteractor {
    fun getPhoto(consumer: StorageConsumer)
    fun savePhotoFromCamera(imageCapture: ImageCapture?, context: Context, consumer: StorageConsumer)
    fun savePhoto(context: Context, bitmap: Bitmap)
    interface StorageConsumer {
        fun consume(imageUri: Uri)
    }
}