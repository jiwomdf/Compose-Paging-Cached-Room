package com.katilijiwoadiwiyono.imagegallerycompose.feature.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.katilijiwoadiwiyono.core.domain.model.ArtWorkModel
import com.katilijiwoadiwiyono.core.utils.PagingUtil.PAGE_LIMIT
import com.katilijiwoadiwiyono.core.utils.PagingUtil.PAGE_LIMIT_SEARCH
import com.katilijiwoadiwiyono.core.utils.PagingUtil.PERFECT_FETCH_DISTANCE
import com.katilijiwoadiwiyono.core.utils.PagingUtil.PERFECT_FETCH_DISTANCE_SEARCH
import com.katilijiwoadiwiyono.core.utils.ResourceState
import com.katilijiwoadiwiyono.imagegallerycompose.R
import com.katilijiwoadiwiyono.imagegallerycompose.feature.FakeMainViewModel
import com.katilijiwoadiwiyono.imagegallerycompose.feature.IMainViewModel
import com.katilijiwoadiwiyono.imagegallerycompose.feature.common.CenterText
import com.katilijiwoadiwiyono.imagegallerycompose.feature.common.SnackbarErrorVisuals
import com.katilijiwoadiwiyono.imagegallerycompose.feature.common.darkModeState
import com.katilijiwoadiwiyono.imagegallerycompose.feature.common.items
import com.katilijiwoadiwiyono.imagegallerycompose.feature.common.setToggleTheme
import com.katilijiwoadiwiyono.imagegallerycompose.feature.common.snackBarError
import com.katilijiwoadiwiyono.imagegallerycompose.feature.dashboard.components.ListImageItem
import com.katilijiwoadiwiyono.imagegallerycompose.feature.dashboard.components.ListImageItemShimmer
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

@OptIn(ExperimentalComposeUiApi::class, ExperimentalCoilApi::class)
@Composable
fun DashboardScreen(
    viewModel: IMainViewModel
) {
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val text by viewModel.text.collectAsState()
    val debounceText by viewModel.debounceText.collectAsState("")
    val searchResult by viewModel.searchResult.collectAsState()
    val isSearchLoading by viewModel.isSearchLoading.collectAsState()
    val isSearchMode = text.isNotEmpty()
    val keyboardController = LocalSoftwareKeyboardController.current

    var artWork: LazyPagingItems<ArtWorkModel>? = null

    if(!isSearchMode) {
        artWork = viewModel.getArtwork(PERFECT_FETCH_DISTANCE, PAGE_LIMIT).collectAsLazyPagingItems()
    }

    LaunchedEffect(debounceText) {
        if(debounceText.isNotEmpty()){
            keyboardController?.hide()
            viewModel.searchArtwork(debounceText, PERFECT_FETCH_DISTANCE_SEARCH, PAGE_LIMIT_SEARCH)
        }
    }

    LaunchedEffect(debounceText) {
        snackBarError(context, snackBarHostState,
            searchResult.resourceState, "something went wrong")
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val isDark = darkModeState.value
                SearchBar(
                    modifier = Modifier,
                    search = text,
                    onValueChange = {
                        scope.launch {
                            viewModel.text.value = it
                            viewModel.cancelSearchIfThereANewRequest()
                        }
                    },
                )
                Switch(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 20.dp)
                        .height(50.dp),
                    colors  = SwitchDefaults.colors(
                        checkedTrackColor = Color.Red,
                    ),
                    checked = isDark,
                    onCheckedChange = {
                        setToggleTheme(isDark)
                    }
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState, snackbar = { snackbarData: SnackbarData ->
                val customVisuals = snackbarData.visuals as? SnackbarErrorVisuals
                if (customVisuals != null) {
                    Snackbar(
                        snackbarData = snackbarData,
                        contentColor = customVisuals.contentColor,
                        containerColor = customVisuals.containerColor
                    )
                } else {
                    Snackbar(snackbarData = snackbarData)
                }
            })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding()),
        ) {

            if(isSearchMode) {
                if(isSearchLoading){
                    ListImageItemShimmer()
                    return@Scaffold
                } else if(searchResult.data != null && searchResult.data?.isEmpty() == true) {
                    CenterText(context.getString(R.string.result_empty))
                    return@Scaffold
                }

                when(searchResult.resourceState) {
                    is ResourceState.Loading -> {}
                    is ResourceState.Success -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3)
                        ) {
                            if(!searchResult.data.isNullOrEmpty()) {
                                val list = searchResult.data ?: emptyList()
                                items(list.size) {
                                    ListImageItem(Modifier, list[it])
                                }
                            }
                        }
                    }
                    else -> {
                        CenterText(context.getString(R.string.something_went_wrong))
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
    val isRefreshLoading = loadState?.refresh == LoadState.Loading
    if (isRefreshLoading) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Red80)
        }
    }

    val isAppendLoading = loadState?.append == LoadState.Loading
    if (isAppendLoading) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Red80)
        }
    }

    val ifRefreshError = loadState?.refresh is LoadState.Error
    val ifAppendError = loadState?.append is LoadState.Error
    if (ifRefreshError || ifAppendError) {
        val error = loadState?.let {
            if (it.append is LoadState.Error) {
                (it.append as LoadState.Error).error
            } else {
                (it.refresh as LoadState.Error).error
            }
        }

        Column(
            modifier = modifier.padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = error?.message ?: error.toString(),
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
            Button(
                modifier = modifier.padding(top = 4.dp),
                onClick = {
                    artWork.refresh()
                },
                content = {
                    Text(text = "Refresh")
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Red80
                )
            )
        }
    }
}