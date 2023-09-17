package com.katilijiwoadiwiyono.core.data.repository

import androidx.paging.PagingData
import com.katilijiwoadiwiyono.core.domain.model.ArtWorkModel
import com.katilijiwoadiwiyono.core.utils.Resource
import com.katilijiwoadiwiyono.core.utils.ResourceState
import kotlinx.coroutines.flow.Flow

interface ArtRepository {
    fun getArtworks(fetchDistance: Int, limit: Int): Flow<PagingData<ArtWorkModel>>
    suspend fun searchArtworks(query: String, fetchDistance: Int, limit: Int): Resource<List<ArtWorkModel>>
    suspend fun getArtworkById(id: Double): Resource<ArtWorkModel>
}