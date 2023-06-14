package com.delirium.photofilter.ui.menu

import androidx.lifecycle.ViewModel
import com.delirium.photofilter.Menu

class MenuViewModel(private val menu: Menu) : ViewModel() {
    fun openCameraScreen() {
        menu.openCameraScreen()
    }

    fun takePhoto() {
        menu.takePhoto()
    }

    fun savePhoto() {
        menu.savePhoto()
    }

    fun getPhotoFromStorage() {
        menu.getPhotoFromStorage()
    }
}