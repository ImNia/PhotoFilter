package com.delirium.photofilter.presentation

import android.graphics.Bitmap
import androidx.activity.ComponentActivity
import com.delirium.photofilter.Creator
import com.delirium.photofilter.SaveMenu

class EditorPresenter(private val activity: ComponentActivity, view: SaveMenu) {
    private val storageInteractor = Creator.provideStorageInteractor(activity)

    fun savePhotoInStorage(image: Bitmap) {
        storageInteractor.savePhoto(activity.baseContext, image)
    }

}