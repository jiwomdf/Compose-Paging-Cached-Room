package com.katilijiwoadiwiyono.imagegallerycompose.feature.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.katilijiwoadiwiyono.imagegallerycompose.feature.FakeMainViewModel
import com.katilijiwoadiwiyono.imagegallerycompose.feature.IMainViewModel
import com.katilijiwoadiwiyono.imagegallerycompose.feature.common.items
import com.katilijiwoadiwiyono.imagegallerycompose.feature.dashboard.components.ListImageItem
import com.katilijiwoadiwiyono.imagegallerycompose.feature.dashboard.components.SearchBar
import com.katilijiwoadiwiyono.imagegallerycompose.ui.theme.Red80


@Preview
@Composable
fun DashboardScreenPreview() {
    DashboardScreen(
        mainViewModel = FakeMainViewModel()
    )
}

@Composable
fun DashboardScreen(
    mainViewModel: IMainViewModel
) {
    val query = ""
    val artWork = if(query.isEmpty()){
        mainViewModel.getArtwork(1, 15).collectAsLazyPagingItems()
    } else {
        mainViewModel.searchArtwork(query, 1, 15).collectAsLazyPagingItems()
    }

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
                items(
                    items = artWork,
                    key = { it.id }
                ) {
                    ListImageItem(
                        modifier = Modifier,
                        artWork = it
                    )
                }

                val loadState = artWork.loadState.mediator
                item {
                    if (loadState?.refresh == LoadState.Loading) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(8.dp),
                                text = "Refresh Loading"
                            )
                            CircularProgressIndicator(color = Red80)
                        }
                    }

                    if (loadState?.append == LoadState.Loading) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator(color = Red80)
                        }
                    }

                    if (loadState?.refresh is LoadState.Error || loadState?.append is LoadState.Error) {
                        val isPaginatingError = (loadState.append is LoadState.Error) || artWork.itemCount > 1
                        val error = if (loadState.append is LoadState.Error)
                            (loadState.append as LoadState.Error).error
                        else
                            (loadState.refresh as LoadState.Error).error

                        val modifier = if (isPaginatingError) {
                            Modifier.padding(8.dp)
                        } else {
                            Modifier.fillMaxSize()
                        }
                        Column(
                            modifier = modifier,
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            if (!isPaginatingError) {
                                Icon(
                                    modifier = Modifier
                                        .size(64.dp),
                                    imageVector = Icons.Rounded.Warning, contentDescription = null
                                )
                            }

                            Text(
                                modifier = Modifier
                                    .padding(8.dp),
                                text = error.message ?: error.toString(),
                                textAlign = TextAlign.Center,
                            )

                            Button(
                                onClick = {
                                    artWork.refresh()
                                },
                                content = {
                                    Text(text = "Refresh")
                                },
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.White,
                                )
                            )
                        }
                    }
                }



            }
        }
    }

}