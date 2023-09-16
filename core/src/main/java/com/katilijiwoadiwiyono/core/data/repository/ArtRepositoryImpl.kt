package com.katilijiwoadiwiyono.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.RoomDatabase
import com.katilijiwoadiwiyono.core.data.local.entity.ArtWorkEntity
import com.katilijiwoadiwiyono.core.data.local.source.LocalDataSource
import com.katilijiwoadiwiyono.core.data.mediator.ArtWorkMediator
import com.katilijiwoadiwiyono.core.data.remote.source.RemoteDataSource
import com.katilijiwoadiwiyono.core.domain.model.ArtWorkModel
import com.katilijiwoadiwiyono.core.utils.PagingUtil
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArtRepositoryImpl @Inject constructor(
    private val database: RoomDatabase,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): ArtRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getArtwork(page: Int, limit: Int): Flow<PagingData<ArtWorkEntity>> =
        Pager(
            config = PagingConfig(
                pageSize = PagingUtil.PAGE_SIZE,
                prefetchDistance = 10,
                initialLoadSize = PagingUtil.PAGE_SIZE, // How many items you want to load initially
            ),
            pagingSourceFactory = {
                // The pagingSourceFactory lambda should always return a brand new PagingSource
                // when invoked as PagingSource instances are not reusable.
                localDataSource.getArtWork()
            },
            remoteMediator = ArtWorkMediator(
                database,
                remoteDataSource,
                localDataSource
            )
        ).flow
}