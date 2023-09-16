package com.katilijiwoadiwiyono.core.data.repository

import androidx.paging.PagingData
import com.katilijiwoadiwiyono.core.data.local.entity.ArtWorkEntity
import kotlinx.coroutines.flow.Flow

interface ArtRepository {
    fun getArtwork(page: Int, limit: Int): Flow<PagingData<ArtWorkEntity>>
}