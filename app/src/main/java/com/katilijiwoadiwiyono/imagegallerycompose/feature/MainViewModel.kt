package com.katilijiwoadiwiyono.imagegallerycompose.feature

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.katilijiwoadiwiyono.core.domain.model.ArtWorkModel
import com.katilijiwoadiwiyono.core.utils.PagingUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(

): ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    fun getPopularMovies(): Flow<PagingData<ArtWorkModel>> =
        Pager(
            config = PagingConfig(
                pageSize = PagingUtil.PAGE_SIZE,
                prefetchDistance = 10,
                initialLoadSize = PagingUtil.PAGE_SIZE, // How many items you want to load initially
            ),
            pagingSourceFactory = {
                // The pagingSourceFactory lambda should always return a brand new PagingSource
                // when invoked as PagingSource instances are not reusable.
                moviesDatabase.getMoviesDao().getMovies()
            },
            remoteMediator = MoviesRemoteMediator(
                moviesApiService,
                moviesDatabase,
            )
        ).flow

}