package com.katilijiwoadiwiyono.core.domain.usecase

import androidx.paging.PagingData
import com.katilijiwoadiwiyono.core.data.repository.ArtRepository
import com.katilijiwoadiwiyono.core.domain.model.ArtWorkModel
import com.katilijiwoadiwiyono.core.utils.Resource
import com.katilijiwoadiwiyono.core.utils.ResourceState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArtIteractor @Inject constructor(
    private val repository: ArtRepository
): ArtUseCase {
    override fun getArtwork(fetchDistance: Int, limit: Int): Flow<PagingData<ArtWorkModel>> {
        return repository.getArtwork(fetchDistance, limit)
    }

    override suspend fun searchArtwork(
        query: String,
        fetchDistance: Int,
        limit: Int
    ): Resource<List<ArtWorkModel>> {
        return repository.searchArtwork(query, fetchDistance, limit)
    }
}