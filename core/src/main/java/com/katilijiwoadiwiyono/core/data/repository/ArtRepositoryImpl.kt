package com.katilijiwoadiwiyono.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.katilijiwoadiwiyono.core.data.local.ImageGalleryRoom
import com.katilijiwoadiwiyono.core.data.mediator.ArtWorkMediator
import com.katilijiwoadiwiyono.core.data.remote.source.RemoteDataSource
import com.katilijiwoadiwiyono.core.domain.model.ArtWorkModel
import com.katilijiwoadiwiyono.core.utils.Resource
import com.katilijiwoadiwiyono.core.utils.callResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArtRepositoryImpl @Inject constructor(
    private val database: ImageGalleryRoom,
    private val remoteDataSource: RemoteDataSource,
): ArtRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getArtworks(fetchDistance: Int, limit: Int): Flow<PagingData<ArtWorkModel>> =
        Pager(
            config = PagingConfig(
                pageSize = limit,
                prefetchDistance = fetchDistance,
                initialLoadSize = limit,
            ),
            pagingSourceFactory = {
                database.artWorkDao().getArtWork()
            },
            remoteMediator = ArtWorkMediator(
                database = database,
                remoteDataSource = remoteDataSource,
                pageLimit = limit
            )
        ).flow.map { pagingData ->
            pagingData.map out@{ userEntity ->
                return@out ArtWorkModel.mapArtWorkModel(userEntity)
            }
        }

    override suspend fun searchArtworks(query: String, fetchDistance: Int, limit: Int): Resource<List<ArtWorkModel>> {
        return callResponse(
            call = {
                remoteDataSource.searchArtworks(query, fetchDistance, limit)
            },
            mapData = {
                ArtWorkModel.mapArtWorkModel(it)
            }
        )
    }

    override suspend fun getArtworkById(id: Double): Resource<ArtWorkModel> {
        return callResponse(
            call = {
               remoteDataSource.getArtworkById(id)
            },
            mapData = {
                ArtWorkModel.mapArtWorkModel(it)
            }
        )
    }
}