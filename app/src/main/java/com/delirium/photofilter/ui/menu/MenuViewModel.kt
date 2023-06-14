package com.delirium.photofilter.ui.menu

import androidx.lifecycle.ViewModel
import com.delirium.photofilter.Menu

class MenuViewModel(val menu: Menu) : ViewModel() {
    fun openCameraScreen() {
        menu.openCameraScreen()
    }

    fun takePhoto() {
        menu.takePhoto()
    }
}