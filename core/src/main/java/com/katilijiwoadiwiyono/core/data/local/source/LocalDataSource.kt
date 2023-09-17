package com.katilijiwoadiwiyono.core.data.local.source

import androidx.paging.PagingSource
import com.katilijiwoadiwiyono.core.data.local.entity.ArtWorkEntity
import com.katilijiwoadiwiyono.core.data.local.entity.RemoteKeysEntity

interface LocalDataSource {
    suspend fun insertArtWork(artWorkEntities: List<ArtWorkEntity>)
    fun getArtWork(): PagingSource<Int, ArtWorkEntity>
    suspend fun clearAllArtWork()
    suspend fun insertAll(remoteKey: List<RemoteKeysEntity>)
    suspend fun getRemoteKeyByMovieId(id: String): RemoteKeysEntity?
    suspend fun clearRemoteKeys()
    suspend fun getCreationTime(): Long?
}