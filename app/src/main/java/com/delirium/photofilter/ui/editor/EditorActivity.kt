package com.delirium.photofilter.ui.editor

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.delirium.photofilter.Creator
import com.delirium.photofilter.R
import com.delirium.photofilter.SaveMenu
import com.delirium.photofilter.ui.theme.PhotoFilterTheme
import com.uvstudio.him.photofilterlibrary.PhotoFilter
import java.io.ByteArrayOutputStream


class EditorActivity() : ComponentActivity(), SaveMenu {
    private val photoFilter = PhotoFilter()
    private var photoFilterList: List<Pair<Int, Bitmap>> = listOf()
    private val presenter = Creator.provideEditorPresentation(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        val uri = Uri.parse(intent.getStringExtra("URI_IMAGE"))

        val image = convertUriToBitmap(context = baseContext, uri = uri)
            ?: BitmapFactory.decodeResource(
                resources,
                R.drawable.image
            )

        photoFilterList = createPhotoFilterList(photoFilter, image)
        editorScreen(image)
    }

    fun editorScreen(image: Bitmap) {
        setContent {
            PhotoFilterTheme() {
                FilterEditorView(image = image, filters = photoFilterList, view = this)
            }
        }
    }

    fun closeScreen() {
        finish()
    }

    fun openSaveScreen(image: Bitmap) {
        setContent {
            PhotoFilterTheme() {
                SaveScreen(view = this, image)
            }
        }
    }

    fun savePhoto(image: Bitmap) {
        presenter.savePhotoInStorage(image = image)
        closeScreen()
    }
    fun sendMessage(image: Bitmap) {
        Intent(Intent.ACTION_SENDTO).apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, getString(R.string.text_message))
            putExtra(Intent.EXTRA_STREAM, convertBitmapToUri(applicationContext, image))
            type = "image/jpeg"
            startActivity(this)
        }
        closeScreen()
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

    private fun convertUriToBitmap(context: Context, uri: Uri?): Bitmap? {
        val input = context.contentResolver.openInputStream(uri!!) ?: return null
        return BitmapFactory.decodeStream(input)
    }

    private fun convertBitmapToUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
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