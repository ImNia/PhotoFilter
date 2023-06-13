package com.delirium.photofilter.ui.menu

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.delirium.photofilter.R
import com.delirium.photofilter.ui.theme.GrayColor
import com.delirium.photofilter.ui.theme.LightGrayColor
import com.delirium.photofilter.ui.theme.PhotoFilterTheme

@Composable
fun MenuButton(viewModel: MenuViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = { viewModel.takePhoto() },
            contentPadding = PaddingValues(20.dp),
            shape = MaterialTheme.shapes.extraLarge,
            colors = ButtonDefaults.buttonColors(
                containerColor = LightGrayColor,
            ),
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.make_photo),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = GrayColor,
            )
        }

        Button(
            onClick = { /*TODO*/ },
            contentPadding = PaddingValues(20.dp),
            shape = MaterialTheme.shapes.extraLarge,
            colors = ButtonDefaults.buttonColors(
                containerColor = LightGrayColor,
            ),
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.take_photo_storage),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = GrayColor,
            )
        }
    }
}

/*@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PhotoFilterTheme {
        MenuButton(MenuViewModel())
    }
}*/
