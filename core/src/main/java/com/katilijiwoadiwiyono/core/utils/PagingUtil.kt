package com.katilijiwoadiwiyono.core.utils

import androidx.paging.PagingState
import com.katilijiwoadiwiyono.core.data.local.dao.RemoteKeysDao
import com.katilijiwoadiwiyono.core.data.local.entity.ArtWorkEntity
import com.katilijiwoadiwiyono.core.data.local.entity.RemoteKeysEntity

object PagingUtil {

    const val PAGE_LIMIT_SEARCH = 20
    const val PERFECT_FETCH_DISTANCE_SEARCH = 10

    const val FIRST_PAGE = 1
    const val PAGE_LIMIT = 50
    const val PERFECT_FETCH_DISTANCE = 40

    const val ERROR_CONNECTION_MESSAGE = "error connection"

    suspend fun RemoteKeysDao?.getRemoteKeyForLastItem(
        state: PagingState<Int, ArtWorkEntity>
    ): RemoteKeysEntity? {
        val lastPageSourceData = state.pages.lastOrNull { it.data.isNotEmpty() } ?: return null
        val lastId = lastPageSourceData.data.lastOrNull()?.id ?: return null
        return this?.getRemoteKeyById(lastId)
    }

    suspend fun RemoteKeysDao?.getRemoteKeyForFirstItem(
        state: PagingState<Int, ArtWorkEntity>
    ): RemoteKeysEntity? {
        val lastPageSourceData = state.pages.firstOrNull { it.data.isNotEmpty() } ?: return null
        val firstId = lastPageSourceData.data.firstOrNull()?.id ?: return null
        return this?.getRemoteKeyById(firstId)
    }

    suspend fun RemoteKeysDao?.getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ArtWorkEntity>
    ): RemoteKeysEntity? {
        val anchorPosition = state.anchorPosition ?: return null
        val closestId = state.closestItemToPosition(anchorPosition)?.id ?: return null
        return this?.getRemoteKeyById(closestId)
    }
}