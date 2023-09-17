package com.katilijiwoadiwiyono.core.domain.usecase

import androidx.paging.PagingData
import com.katilijiwoadiwiyono.core.domain.model.ArtWorkModel
import com.katilijiwoadiwiyono.core.utils.Resource
import com.katilijiwoadiwiyono.core.utils.ResourceState
import kotlinx.coroutines.flow.Flow

interface ArtUseCase {
    fun getArtwork(fetchDistance: Int, limit: Int): Flow<PagingData<ArtWorkModel>>
    suspend fun searchArtwork(query: String, fetchDistance: Int, limit: Int): Resource<List<ArtWorkModel>>
}