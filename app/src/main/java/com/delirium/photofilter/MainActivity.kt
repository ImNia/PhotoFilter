package com.delirium.photofilter

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.delirium.photofilter.ui.menu.MenuButton
import com.delirium.photofilter.ui.menu.MenuViewModel
import com.delirium.photofilter.ui.theme.PhotoFilterTheme


class MainActivity : ComponentActivity(), Menu {
    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {bitmap ->
            val intent = Intent(this, EditorPhoto::class.java)
            intent.putExtra("image", bitmap)
            startActivity(intent)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoFilterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MenuButton(MenuViewModel(this))
                }
            }
        }
    }

    private fun openSomeActivityForResult() {
//        val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncher.launch(null)
    }

    override fun takeFoto() {
        openSomeActivityForResult()
    }
}