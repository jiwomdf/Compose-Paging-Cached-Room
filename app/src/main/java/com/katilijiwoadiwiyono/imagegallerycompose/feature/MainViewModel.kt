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
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


interface IMainViewModel {
    fun getArtwork(page: Int, limit: Int): Flow<PagingData<ArtWorkModel>>
    fun searchArtwork(query: String, page: Int, limit: Int): Flow<PagingData<ArtWorkModel>>
}

class FakeMainViewModel: IMainViewModel {
    override fun getArtwork(page: Int, limit: Int): Flow<PagingData<ArtWorkModel>> {
        return flow {  }
    }

    override fun searchArtwork(
        query: String,
        page: Int,
        limit: Int
    ): Flow<PagingData<ArtWorkModel>> {
        return flow {  }
    }

}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: ArtUseCase
): ViewModel(), IMainViewModel {

    override fun getArtwork(page: Int, limit: Int): Flow<PagingData<ArtWorkModel>> =
        useCase.getArtwork(page, limit)

    override fun searchArtwork(
        query: String,
        page: Int,
        limit: Int
    ): Flow<PagingData<ArtWorkModel>> =
        useCase.searchArtwork(query, page, limit)
}