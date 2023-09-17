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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.katilijiwoadiwiyono.core.domain.model.ArtWorkModel
import com.katilijiwoadiwiyono.imagegallerycompose.ui.theme.BackgroundGrey500


@Preview
@Composable
fun ListImageItemScreen() {
    ListImageItem(
        modifier = Modifier,
        artWork = ArtWorkModel(
            imageId = "9484feef-7c7f-e70f-4d5e-4320ece4bfd1",
            title = "Two Studies of a Roma Woman and a Roma Boy in a Large Hat",
            description = "<p>Jacques De Gheyn II employed a stylized graphic language of hatching and cross-hatching to capture the individual features and garments of the figures represented here, which include a woman shown from two vantage points and a young boy. The artist used iron gall ink made from the protective growth an oak tree produces after a wasp lays its eggs inside the tree branch. The ink is deep brown and almost black in some areas. Because iron gall ink contains a high concentration of tannic acid, however, it dissolves paper over time. Above the right foot of the Roma woman the damaged paper was repaired.</p> <p>On several occasions De Gheyn represented figures from the Roma or Romani community, itinerant members of Dutch society whose origins can be traced to northern India. Often finding shelter at the outskirts of cities, the Roma frequently took up informal trades like peddling and fortune telling.</p> ",
            imageUrl = "https://www.artic.edu/iiif/2/9484feef-7c7f-e70f-4d5e-4320ece4bfd1/full/200,/0/default.jpg",
        )
    )
}

@Composable
fun ListImageItem(
    modifier: Modifier,
    artWork: ArtWorkModel?
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
                    .data(artWork?.imageUrl ?: "")
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )
        }
    }
}