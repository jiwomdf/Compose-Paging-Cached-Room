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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.katilijiwoadiwiyono.core.domain.model.ArtWorkModel
import com.katilijiwoadiwiyono.core.utils.PagingUtil.PAGE_LIMIT
import com.katilijiwoadiwiyono.core.utils.PagingUtil.PERFECT_FETCH_DISTANCE
import com.katilijiwoadiwiyono.core.utils.Resource
import com.katilijiwoadiwiyono.imagegallerycompose.feature.FakeMainViewModel
import com.katilijiwoadiwiyono.imagegallerycompose.feature.IMainViewModel
import com.katilijiwoadiwiyono.imagegallerycompose.feature.common.items
import com.katilijiwoadiwiyono.imagegallerycompose.feature.common.snackBarError
import com.katilijiwoadiwiyono.imagegallerycompose.feature.dashboard.components.ListImageItem
import com.katilijiwoadiwiyono.imagegallerycompose.feature.dashboard.components.SearchBar
import com.katilijiwoadiwiyono.imagegallerycompose.ui.theme.Red80
import kotlinx.coroutines.launch


@Preview
@Composable
fun DashboardScreenPreview() {
    DashboardScreen(
        viewModel = FakeMainViewModel()
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DashboardScreen(
    viewModel: IMainViewModel
) {
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val text by viewModel.text.collectAsState()
    val searchResult by viewModel.searchResult
    val debounceText by viewModel.debounceText.collectAsState("")
    val isSearchMode = debounceText.isNotEmpty()
    val keyboardController = LocalSoftwareKeyboardController.current

    var artWork: LazyPagingItems<ArtWorkModel>? = null

    if(isSearchMode) {
        viewModel.searchArtwork(debounceText, PERFECT_FETCH_DISTANCE, PAGE_LIMIT)
    } else {
        artWork = viewModel.getArtwork(PERFECT_FETCH_DISTANCE, PAGE_LIMIT).collectAsLazyPagingItems()
    }

    LaunchedEffect(searchResult) {
        keyboardController?.hide()
        snackBarError(context, snackBarHostState, searchResult, "something went wrong")
    }

    Scaffold(
        topBar = {
            SearchBar(
                modifier = Modifier,
                search = text,
                onValueChange = {
                    scope.launch {
                        viewModel.text.value = it
                    }
                },
            )
        },
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding()),
        ) {

            if(isSearchMode) {
                when(searchResult) {
                    is Resource.Loading -> {
                        CircularProgressIndicator()
                    }
                    is Resource.Success -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3)
                        ) {
                            val data = (searchResult as Resource.Success).data
                            items(data.size) {
                                ListImageItem(Modifier, data[it])
                            }
                        }
                    }
                    else -> {
                        Text(text = "something went wrong")
                    }
                }
                return@Scaffold
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(3)
            ) {
                artWork?.let {
                    val loadState = artWork.loadState.mediator
                    items(artWork, { it.id }) {
                        ListImageItem(Modifier, it)
                    }
                    item {
                        PagingLoadingState(Modifier, loadState, artWork)
                    }
                }
            }
        }
    }

}

@Composable
fun PagingLoadingState(
    modifier: Modifier,
    loadState: LoadStates?,
    artWork: LazyPagingItems<ArtWorkModel>
) {
    if (loadState?.refresh == LoadState.Loading) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
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
            contentAlignment = Alignment.Center
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
                    modifier = Modifier.size(64.dp),
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