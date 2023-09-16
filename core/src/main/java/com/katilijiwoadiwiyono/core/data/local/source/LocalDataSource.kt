package com.katilijiwoadiwiyono.core.data.local.source

import androidx.paging.PagingSource
import com.katilijiwoadiwiyono.core.data.local.entity.ArtWorkEntity
import com.katilijiwoadiwiyono.core.data.local.entity.RemoteKeys

interface LocalDataSource {
    suspend fun insertArtWork(artWorkEntities: List<ArtWorkEntity>)
    fun getArtWork(): PagingSource<Int, ArtWorkEntity>
    suspend fun clearAllArtWork()
    suspend fun insertAll(remoteKey: List<RemoteKeys>)
    suspend fun getRemoteKeyByMovieID(id: Int): RemoteKeys?
    suspend fun clearRemoteKeys()
    suspend fun getCreationTime(): Long?
}