package com.delirium.photofilter.ui.editor

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.delirium.photofilter.R
import com.delirium.photofilter.ui.theme.BackgroundButtonColor
import com.delirium.photofilter.ui.theme.BackgroundScreenColor
import com.delirium.photofilter.ui.theme.TextOnButtonColor

@Composable
fun SaveScreen(view: EditorActivity, image: Bitmap) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundScreenColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = { view.sendMessage(image) },
            contentPadding = PaddingValues(20.dp),
            shape = MaterialTheme.shapes.extraLarge,
            colors = ButtonDefaults.buttonColors(
                containerColor = BackgroundButtonColor,
            ),
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.send_message_photo),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = TextOnButtonColor,
            )
        }
        Button(
            onClick = { view.savePhoto(image) },
            contentPadding = PaddingValues(20.dp),
            shape = MaterialTheme.shapes.extraLarge,
            colors = ButtonDefaults.buttonColors(
                containerColor = BackgroundButtonColor,
            ),
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.save_on_phone_photo),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = TextOnButtonColor,
            )
        }
        Button(
            onClick = { view.editorScreen(image) },
            contentPadding = PaddingValues(20.dp),
            shape = MaterialTheme.shapes.extraLarge,
            colors = ButtonDefaults.buttonColors(
                containerColor = BackgroundButtonColor,
            ),
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.return_on_screen),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = TextOnButtonColor,
            )
        }
    }
}