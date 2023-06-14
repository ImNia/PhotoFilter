package com.delirium.photofilter.presentation

import android.content.Context
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.camera.core.ImageCapture
import com.delirium.photofilter.Creator
import com.delirium.photofilter.MainMenu
import com.delirium.photofilter.storage.api.StorageInteractor

class MenuPresenter(activity: ComponentActivity, private val menu: MainMenu) {
    private val storageInteractor = Creator.provideStorageInteractor(activity)

    fun openStorage() {
        storageInteractor.getPhoto(
            object : StorageInteractor.StorageConsumer {
                override fun consume(imageUri: Uri) {
                    menu.getPhotoFromStorage(imageUri)
                }
            }
        )
    }

    fun savePhotoInStorage(imageCapture: ImageCapture?, context: Context) {
        storageInteractor.savePhoto(
            imageCapture = imageCapture,
            context = context,
            object : StorageInteractor.StorageConsumer {
                override fun consume(imageUri: Uri) {
                    menu.savePhoto(imageUri)
                }
            }
        )
    }
}