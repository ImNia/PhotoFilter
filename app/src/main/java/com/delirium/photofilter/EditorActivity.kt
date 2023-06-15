package com.delirium.photofilter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.delirium.photofilter.ui.editor.FilterEditorView
import com.delirium.photofilter.ui.theme.PhotoFilterTheme
import com.uvstudio.him.photofilterlibrary.PhotoFilter

class EditorActivity() : ComponentActivity() {
    private val photoFilter = PhotoFilter()
    private var photoFilterList: List<Pair<Int, Bitmap>> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        val uri = Uri.parse(intent.getStringExtra("URI_IMAGE"))

        val image = getContactBitmapFromURI(context = baseContext, uri = uri)
            ?: BitmapFactory.decodeResource(
                resources,
                R.drawable.image
            )

        photoFilterList = createPhotoFilterList(photoFilter, image)
        editorScreen(image)
    }

    private fun editorScreen(image: Bitmap) {
        setContent {
            PhotoFilterTheme() {
                FilterEditorView(image = image, filters = photoFilterList, view = this)
            }
        }
    }

    fun closeScreen() {
        finish()
    }

    fun changeFilter(index: Int) {
        editorScreen(photoFilterList.get(index).second)
    }

    private fun createPhotoFilterList(
        photoFilter: PhotoFilter,
        image: Bitmap
    ): List<Pair<Int, Bitmap>> {
        return listOf(
            Pair(ONE, photoFilter.one(this, image)),
            Pair(TWO, photoFilter.two(this, image)),
            Pair(THREE, photoFilter.three(this, image)),
            Pair(FOUR, photoFilter.four(this, image)),
            Pair(FIVE, photoFilter.five(this, image)),
            Pair(SIX, photoFilter.six(this, image)),
            Pair(SEVEN, photoFilter.seven(this, image)),
            Pair(EIGHT, photoFilter.eight(this, image)),
            Pair(NINE, photoFilter.ten(this, image))
        )
    }

    private fun getContactBitmapFromURI(context: Context, uri: Uri?): Bitmap? {
        val input = context.contentResolver.openInputStream(uri!!) ?: return null
        return BitmapFactory.decodeStream(input)
    }

    companion object {
        const val ONE = 0
        const val TWO = 1
        const val THREE = 2
        const val FOUR = 3
        const val FIVE = 4
        const val SIX = 5
        const val SEVEN = 6
        const val EIGHT = 7
        const val NINE = 8
    }
}