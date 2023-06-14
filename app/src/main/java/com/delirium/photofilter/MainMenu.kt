package com.delirium.photofilter

import android.net.Uri

interface MainMenu {
    fun getPhotoFromStorage(imageUri: Uri)

    fun savePhoto(imageUri: Uri)
}