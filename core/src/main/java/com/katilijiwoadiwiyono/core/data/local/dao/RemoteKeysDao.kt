package com.katilijiwoadiwiyono.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.katilijiwoadiwiyono.core.data.local.entity.RemoteKeysEntity


@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKeys(remoteKey: List<RemoteKeysEntity>)

    @Query("Select * From ms_remote_key Where id = :id")
    suspend fun getRemoteKeyById(id: Double): RemoteKeysEntity?

    @Query("Delete From ms_remote_key")
    suspend fun clearRemoteKeys()

    @Query("Select createdAt From ms_remote_key Order By createdAt DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}