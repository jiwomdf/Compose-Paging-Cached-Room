package com.katilijiwoadiwiyono.core.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.katilijiwoadiwiyono.core.data.local.ImageGalleryRoom
import com.katilijiwoadiwiyono.core.data.local.dao.ArtWorkDao
import com.katilijiwoadiwiyono.core.data.local.dao.RemoteKeysDao
import com.katilijiwoadiwiyono.core.data.local.entity.ArtWorkEntity
import com.katilijiwoadiwiyono.core.data.local.entity.RemoteKeysEntity
import com.katilijiwoadiwiyono.core.data.remote.source.RemoteDataSource
import com.katilijiwoadiwiyono.core.utils.PagingUtil.ERROR_CONNECTION_MESSAGE
import com.katilijiwoadiwiyono.core.utils.PagingUtil.FIRST_PAGE
import com.katilijiwoadiwiyono.core.utils.PagingUtil.getRemoteKeyClosestToCurrentPosition
import com.katilijiwoadiwiyono.core.utils.PagingUtil.getRemoteKeyForFirstItem
import com.katilijiwoadiwiyono.core.utils.PagingUtil.getRemoteKeyForLastItem
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class ArtWorkMediator(
    private val database: ImageGalleryRoom,
    private val remoteDataSource: RemoteDataSource,
    private val pageLimit: Int
) : RemoteMediator<Int, ArtWorkEntity>() {

    private var artworkDao: ArtWorkDao? = null
    private var remoteKeysDao: RemoteKeysDao? = null

    init {
        artworkDao = database.artWorkDao()
        remoteKeysDao = database.remoteKeysDao()
    }

    @ExperimentalPagingApi
    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        val timeHasSpentSinceLastInsert = System.currentTimeMillis() - (remoteKeysDao?.getCreationTime() ?: 0)
        val isHasNotTimeOut = timeHasSpentSinceLastInsert < cacheTimeout
        return if (isHasNotTimeOut) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArtWorkEntity>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = remoteKeysDao.getRemoteKeyClosestToCurrentPosition(state)
                val lastPage = remoteKeys?.nextKey?.minus(1) ?: FIRST_PAGE
                lastPage
            }
            LoadType.PREPEND -> {
                val remoteKeys = remoteKeysDao.getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                val isReachEndOfPagination = remoteKeys != null
                val result = prevKey ?: return MediatorResult.Success(endOfPaginationReached = isReachEndOfPagination)
                result
            }
            LoadType.APPEND -> {
                val remoteKeys = remoteKeysDao.getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                val isReachEndOfPagination = remoteKeys != null
                val result = nextKey ?: return MediatorResult.Success(endOfPaginationReached = isReachEndOfPagination)
                result
            }
        }

        try {
            val apiResponse = remoteDataSource.getArtwork(page = page, pageLimit)
            if(apiResponse.isSuccessful) {
                val artworkResponse = apiResponse.body()
                val endOfPaginationReached = artworkResponse?.artworkResponse.isNullOrEmpty()

                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        remoteKeysDao?.clearRemoteKeys()
                        artworkDao?.clearAllArtWork()
                    }
                    val remoteKeys = RemoteKeysEntity.mapRemoteKeysEntity(
                        response = artworkResponse?.artworkResponse,
                        prevKey = if (page > FIRST_PAGE) page - 1 else null,
                        nextKey = if (endOfPaginationReached) null else page + 1,
                        page = page
                    )
                    remoteKeys?.let {
                        remoteKeysDao?.insertRemoteKeys(it)
                        val remotePage = artworkResponse?.paginationResponse?.currentPage
                        val response = artworkResponse?.artworkResponse
                        if(remotePage != null && response != null) {
                            val entity = ArtWorkEntity.mapArtWorkModel(response, remotePage)
                            artworkDao?.insertArtWork(entity)
                        }
                    }
                }
                return MediatorResult.Success(endOfPaginationReached = false)
            } else {
                return MediatorResult.Error(Exception(ERROR_CONNECTION_MESSAGE))
            }
        } catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            return MediatorResult.Error(error)
        }
    }
}