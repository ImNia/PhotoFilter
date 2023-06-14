package com.delirium.photofilter.ui.editor

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.delirium.photofilter.EditorActivity
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