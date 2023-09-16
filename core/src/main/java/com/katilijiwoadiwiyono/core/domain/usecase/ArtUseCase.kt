package com.katilijiwoadiwiyono.core.domain.usecase

import androidx.paging.PagingData
import com.katilijiwoadiwiyono.core.data.local.entity.ArtWorkEntity
import kotlinx.coroutines.flow.Flow

interface ArtUseCase {
    fun getArtwork(page: Int, limit: Int): Flow<PagingData<ArtWorkEntity>>
}