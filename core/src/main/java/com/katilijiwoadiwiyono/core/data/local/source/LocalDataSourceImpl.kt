package com.katilijiwoadiwiyono.core.data.local.source

import androidx.paging.PagingSource
import com.katilijiwoadiwiyono.core.data.local.dao.ArtWorkDao
import com.katilijiwoadiwiyono.core.data.local.dao.RemoteKeysDao
import com.katilijiwoadiwiyono.core.data.local.entity.ArtWorkEntity
import com.katilijiwoadiwiyono.core.data.local.entity.RemoteKeys
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val artWorkDao: ArtWorkDao,
    private val remoteKeysDao: RemoteKeysDao
): LocalDataSource {
    override suspend fun insertArtWork(artWorkEntities: List<ArtWorkEntity>) {
        artWorkDao.insertArtWork(artWorkEntities)
    }

    override fun getArtWork(): PagingSource<Int, ArtWorkEntity> {
        return artWorkDao.getArtWork()
    }

    override suspend fun clearAllArtWork() {
        artWorkDao.clearAllArtWork()
    }

    override suspend fun insertAll(remoteKey: List<RemoteKeys>) {
        remoteKeysDao.insertRemoteKeys(remoteKey)
    }

    override suspend fun getRemoteKeyByMovieID(id: String): RemoteKeys? {
        return remoteKeysDao.getRemoteKeyByImageId(id)
    }

    override suspend fun clearRemoteKeys() {
        remoteKeysDao.clearRemoteKeys()
    }

    override suspend fun getCreationTime(): Long? {
        return remoteKeysDao.getCreationTime()
    }

}