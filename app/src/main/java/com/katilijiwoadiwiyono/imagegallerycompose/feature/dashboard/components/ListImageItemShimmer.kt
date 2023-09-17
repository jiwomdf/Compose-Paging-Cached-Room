package com.katilijiwoadiwiyono.imagegallerycompose.feature.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.katilijiwoadiwiyono.imagegallerycompose.feature.common.customShimmer

@Preview

@Composable
fun ListImageItemShimmerScreen() {
    ListImageItemShimmer()
}

@Composable
fun ListImageItemShimmer() {
    val listSize = (1..12).map(Integer::toString)
    Column {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3)
        ) {
            items(listSize.size) {
                Column(
                    modifier = Modifier
                        .padding(6.dp)
                        .customShimmer()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                                .background(Color.LightGray)
                        )
                    }
                }
            }
        }
    }
}