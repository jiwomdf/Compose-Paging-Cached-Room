package com.katilijiwoadiwiyono.core.domain.usecase

import androidx.paging.PagingData
import com.katilijiwoadiwiyono.core.data.local.entity.ArtWorkEntity
import com.katilijiwoadiwiyono.core.domain.model.ArtWorkModel
import com.katilijiwoadiwiyono.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ArtUseCase {
    fun getArtwork(fetchDistance: Int, limit: Int): Flow<PagingData<ArtWorkModel>>
    suspend fun searchArtwork(query: String, fetchDistance: Int, limit: Int): Resource<List<ArtWorkModel>>
}