package com.katilijiwoadiwiyono.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.room.RoomDatabase
import com.katilijiwoadiwiyono.core.data.local.ImageGalleryRoom
import com.katilijiwoadiwiyono.core.data.local.entity.ArtWorkEntity
import com.katilijiwoadiwiyono.core.data.local.source.LocalDataSource
import com.katilijiwoadiwiyono.core.data.mediator.ArtWorkMediator
import com.katilijiwoadiwiyono.core.data.remote.source.RemoteDataSource
import com.katilijiwoadiwiyono.core.domain.model.ArtWorkModel
import com.katilijiwoadiwiyono.core.utils.PagingUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArtRepositoryImpl @Inject constructor(
    private val database: ImageGalleryRoom,
    private val remoteDataSource: RemoteDataSource,
): ArtRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getArtwork(page: Int, limit: Int): Flow<PagingData<ArtWorkModel>> =
        Pager(
            config = PagingConfig(
                pageSize = PagingUtil.PAGE_SIZE,
                prefetchDistance = 10,
                initialLoadSize = PagingUtil.PAGE_SIZE, // How many items you want to load initially
            ),
            pagingSourceFactory = {
                database.artWorkDao().getArtWork()
            },
            remoteMediator = ArtWorkMediator(
                database,
                remoteDataSource,
                PagingUtil.PAGE_SIZE
            )
        ).flow.map { pagingData ->
            pagingData.map out@{ userEntity ->
                return@out ArtWorkModel.mapArtWorkModel(userEntity)
            }
        }
}