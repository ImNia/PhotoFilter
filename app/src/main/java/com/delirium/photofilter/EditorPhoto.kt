package com.delirium.photofilter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.asImageBitmap
import com.delirium.photofilter.ui.editor.Editor
import com.delirium.photofilter.ui.theme.PhotoFilterTheme
import java.io.FileNotFoundException


class EditorPhoto() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = getIntent()
        val uri = Uri.parse(intent.getStringExtra("URI_IMAGE"))

        /*val image: Bitmap = intent.getParcelableExtra("image") ?: BitmapFactory.decodeResource(
            getResources(),
            R.drawable.test
        )*/

        val image = getContactBitmapFromURI(context = baseContext, uri = uri)
            ?: BitmapFactory.decodeResource(
                getResources(),
                R.drawable.test
            )
        setContent {
            PhotoFilterTheme() {
                Editor(image = image.asImageBitmap())
            }
        }
    }

    private fun getContactBitmapFromURI(context: Context, uri: Uri?): Bitmap? {
        try {
            val input = context.contentResolver.openInputStream(uri!!) ?: return null
            return BitmapFactory.decodeStream(input)
        } catch (e: FileNotFoundException) {
        }
        return null
    }
}