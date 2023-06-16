package com.delirium.photofilter.ui.editor

import android.graphics.Bitmap
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.delirium.photofilter.ui.theme.BackgroundScreenColor

@Composable
fun Editor(image: ImageBitmap, view: EditorActivity) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundScreenColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            bitmap = image,
            contentDescription = "",
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .fillMaxSize()
                .weight(3f)
                .border(BorderStroke(1.dp, Color.Black))
                .background(BackgroundScreenColor)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = { view.closeScreen() },
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                Text(text = "Close screen")
            }
            TextButton(
                onClick = { /*TODO*/ },
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                Text(text = "Save Photo")
            }
        }
    }
}

@Composable
fun FilterEditorView(image: Bitmap, filters: List<Pair<Int, Bitmap>>, view: EditorActivity) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundScreenColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = { view.closeScreen() },
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                Text(text = "Close screen")
            }
            TextButton(
                onClick = { view.openSaveScreen(image) },
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                Text(text = "Save")
            }
        }
        Image(
            bitmap = image.asImageBitmap(),
            contentDescription = "",
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .fillMaxSize()
                .weight(3f)
                .border(BorderStroke(1.dp, Color.Black))
                .background(BackgroundScreenColor)
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(filters.size) {
                Column {
                    Image(
                        bitmap = filters.get(it).second.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            view.changeFilter(filters.get(it).first)
                        }
                    )
                }
            }
        }
    }
}