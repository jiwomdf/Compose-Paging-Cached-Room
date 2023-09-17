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
                //New Query so clear the DB
                val remoteKeys = remoteKeysDao.getRemoteKeyClosestToCurrentPosition(state)
                val lastPage = remoteKeys?.nextKey?.minus(1) ?: 1
                lastPage
            }
            LoadType.PREPEND -> {
                val remoteKeys = remoteKeysDao.getRemoteKeyForFirstItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                val prevKey = remoteKeys?.prevKey
                val isReachEndOfPagination = remoteKeys != null
                val result = prevKey ?: return MediatorResult.Success(endOfPaginationReached = isReachEndOfPagination)
                result
            }
            LoadType.APPEND -> {
                val remoteKeys = remoteKeysDao.getRemoteKeyForLastItem(state)

                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with endOfPaginationReached = false because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its nextKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey
                val isReachEndOfPagination = remoteKeys != null
                nextKey ?: return MediatorResult.Success(endOfPaginationReached = isReachEndOfPagination)
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
                    val prevKey = if (page > 1) page - 1 else null
                    val nextKey = if (endOfPaginationReached) null else page + 1
                    val remoteKeys = RemoteKeysEntity.mapRemoteKeysEntity(
                        response = artworkResponse?.artworkResponse,
                        prevKey = prevKey,
                        nextKey = nextKey,
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
                return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } else {
                return MediatorResult.Error(Exception("error connection"))
            }
        } catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            return MediatorResult.Error(error)
        }
    }
}