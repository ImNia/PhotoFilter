package com.delirium.photofilter.storage.api

import android.content.Context
import android.net.Uri
import androidx.camera.core.ImageCapture

interface StorageInteractor {
    fun getPhoto(consumer: StorageConsumer)
    fun savePhoto(imageCapture: ImageCapture?, context: Context, consumer: StorageConsumer)

    interface StorageConsumer {
        fun consume(imageUri: Uri)
    }
}