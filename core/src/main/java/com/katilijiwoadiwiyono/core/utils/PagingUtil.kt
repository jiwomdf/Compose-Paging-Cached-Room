package com.katilijiwoadiwiyono.core.utils

import androidx.paging.PagingState
import com.katilijiwoadiwiyono.core.data.local.dao.RemoteKeysDao
import com.katilijiwoadiwiyono.core.data.local.entity.ArtWorkEntity
import com.katilijiwoadiwiyono.core.data.local.entity.RemoteKeysEntity

object PagingUtil {
    const val PAGE_SIZE = 15

    suspend fun RemoteKeysDao?.getRemoteKeyForLastItem(
        state: PagingState<Int, ArtWorkEntity>
    ): RemoteKeysEntity? {
        val lastPageSourceData = state.pages.lastOrNull { it.data.isNotEmpty() } ?: return null
        val lastImageId = lastPageSourceData.data.lastOrNull()?.imageId ?: return null
        return this?.getRemoteKeyByImageId(lastImageId)
    }

    suspend fun RemoteKeysDao?.getRemoteKeyForFirstItem(
        state: PagingState<Int, ArtWorkEntity>
    ): RemoteKeysEntity? {
        val lastPageSourceData = state.pages.firstOrNull { it.data.isNotEmpty() } ?: return null
        val firstImageId = lastPageSourceData.data.firstOrNull()?.imageId ?: return null
        return this?.getRemoteKeyByImageId(firstImageId)
    }

    suspend fun RemoteKeysDao?.getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ArtWorkEntity>
    ): RemoteKeysEntity? {
        val anchorPosition = state.anchorPosition ?: return null
        val closestImageId = state.closestItemToPosition(anchorPosition)?.imageId ?: return null
        return this?.getRemoteKeyByImageId(closestImageId)
    }
}