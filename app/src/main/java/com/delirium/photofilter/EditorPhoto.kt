package com.delirium.photofilter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.asImageBitmap
import com.delirium.photofilter.ui.editor.Editor
import com.delirium.photofilter.ui.theme.PhotoFilterTheme

class EditorPhoto(): ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = getIntent()
        val image: Bitmap = intent.getParcelableExtra("image") ?: BitmapFactory.decodeResource(getResources(), R.drawable.test)
        setContent {
            PhotoFilterTheme() {
                Editor(image = image.asImageBitmap())
            }
        }
    }
}