package com.katilijiwoadiwiyono.imagegallerycompose.feature.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.katilijiwoadiwiyono.imagegallerycompose.ui.theme.BackgroundGrey500


@Preview
@Composable
fun ListImageItemScreen() {
    ListImageItem(
        modifier = Modifier
    )
}

@Composable
fun ListImageItem(
    modifier: Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .padding(6.dp)
    ) {
        Card {
            AsyncImage(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .background(BackgroundGrey500),
                model = ImageRequest.Builder(context)
                    .data("https://www.artic.edu/iiif/2/e966799b-97ee-1cc6-bd2f-a94b4b8bb8f9/full/150,/0/default.jpg")
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )
        }
    }
}