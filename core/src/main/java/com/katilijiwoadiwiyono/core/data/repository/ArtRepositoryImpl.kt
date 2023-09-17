package com.katilijiwoadiwiyono.core.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.katilijiwoadiwiyono.core.data.local.ImageGalleryRoom
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
    override fun getArtwork(query: String, page: Int, limit: Int): Flow<PagingData<ArtWorkModel>> =
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
                PagingUtil.PAGE_SIZE,
                query
            )
        ).flow.map { pagingData ->
            pagingData.map out@{ userEntity ->
                Log.e("jiwo", "getArtwork: $userEntity", )
                return@out ArtWorkModel.mapArtWorkModel(userEntity)
            }
        }
}