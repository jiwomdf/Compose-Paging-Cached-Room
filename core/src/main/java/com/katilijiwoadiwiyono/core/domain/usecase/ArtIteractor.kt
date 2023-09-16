package com.katilijiwoadiwiyono.core.domain.usecase

import androidx.paging.PagingData
import com.katilijiwoadiwiyono.core.data.local.entity.ArtWorkEntity
import com.katilijiwoadiwiyono.core.data.repository.ArtRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArtIteractor @Inject constructor(
    private val repository: ArtRepository
): ArtUseCase {
    override fun getArtwork(page: Int, limit: Int): Flow<PagingData<ArtWorkEntity>> {
        return repository.getArtwork(page, limit)
    }
}