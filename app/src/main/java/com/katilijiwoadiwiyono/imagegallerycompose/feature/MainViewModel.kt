package com.katilijiwoadiwiyono.imagegallerycompose.feature

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.katilijiwoadiwiyono.core.data.local.entity.ArtWorkEntity
import com.katilijiwoadiwiyono.core.domain.model.ArtWorkModel
import com.katilijiwoadiwiyono.core.domain.usecase.ArtUseCase
import com.katilijiwoadiwiyono.core.utils.PagingUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: ArtUseCase
): ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    fun getPopularMovies(page: Int, limit: Int): Flow<PagingData<ArtWorkEntity>> =
        useCase.getArtwork(page, limit)


}