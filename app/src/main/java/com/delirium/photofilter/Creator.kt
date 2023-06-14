package com.delirium.photofilter

import androidx.activity.ComponentActivity
import com.delirium.photofilter.presentation.MenuPresenter
import com.delirium.photofilter.storage.api.StorageInteractor
import com.delirium.photofilter.storage.impl.StorageInteractorImpl

object Creator {
    fun provideStorageInteractor(activity: ComponentActivity): StorageInteractor {
        return StorageInteractorImpl(activity)
    }

    fun provideMenuPresentation(activity: ComponentActivity, menu: MainMenu): MenuPresenter {
        return MenuPresenter(activity, menu)
    }
}