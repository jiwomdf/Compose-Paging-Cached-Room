package com.katilijiwoadiwiyono.imagegallerycompose.feature.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.katilijiwoadiwiyono.imagegallerycompose.feature.dashboard.components.ListImageItem
import com.katilijiwoadiwiyono.imagegallerycompose.feature.dashboard.components.SearchBar


@Preview
@Composable
fun DashboardScreenPreview() {
    DashboardScreen()
}

@Composable
fun DashboardScreen() {

    val data = (1..15).map(Integer::toString)

    Scaffold(
        topBar = {
            SearchBar(
                modifier = Modifier,
                search = "",
                onValueChange = {}
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding()),
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3)
            ) {
                items(data.size) {
                    ListImageItem(
                        modifier = Modifier
                    )
                }
            }
        }
    }

}