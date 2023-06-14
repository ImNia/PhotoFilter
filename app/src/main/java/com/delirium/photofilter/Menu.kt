package com.delirium.photofilter

import android.net.Uri

interface Menu {
    fun openCameraScreen()
    fun takePhoto()
    fun savePhoto()
    fun getPhotoFromStorage()
}