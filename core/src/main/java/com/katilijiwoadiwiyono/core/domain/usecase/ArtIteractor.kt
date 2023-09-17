package com.katilijiwoadiwiyono.core.domain.usecase

import androidx.paging.PagingData
import com.katilijiwoadiwiyono.core.data.repository.ArtRepository
import com.katilijiwoadiwiyono.core.domain.model.ArtWorkModel
import com.katilijiwoadiwiyono.core.utils.Resource
import com.katilijiwoadiwiyono.core.utils.ResourceState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArtIteractor @Inject constructor(
    private val repository: ArtRepository
): ArtUseCase {
    override fun getArtwork(fetchDistance: Int, limit: Int): Flow<PagingData<ArtWorkModel>> =
        repository.getArtworks(fetchDistance, limit)

    override suspend fun searchArtwork(
        query: String,
        fetchDistance: Int,
        limit: Int
    ): Resource<List<ArtWorkModel>> {
        val artworks = repository.searchArtworks(query, fetchDistance, limit)
        return when (artworks.resourceState) {
            is ResourceState.Success -> {
                val listArtWork = mutableListOf<ArtWorkModel>()
                artworks.data?.forEach {
                    val artwork = repository.getArtworkById(it.id)
                    delay(100)
                    when(artwork.resourceState) {
                        is ResourceState.Success -> {
                            artwork.data?.let {
                                listArtWork.add(it)
                            }
                        }
                        else -> {}
                    }
                }
                return Resource(
                    resourceState = ResourceState.Success,
                    data = listArtWork
                )
            }
            else -> artworks
        }
    }
}