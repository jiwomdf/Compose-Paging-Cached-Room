package com.katilijiwoadiwiyono.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.katilijiwoadiwiyono.core.data.local.entity.RemoteKeys


@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKeys(remoteKey: List<RemoteKeys>)

    @Query("Select * From ms_remote_key Where image_id = :id")
    suspend fun getRemoteKeyByImageId(id: String): RemoteKeys?

    @Query("Delete From ms_remote_key")
    suspend fun clearRemoteKeys()

    @Query("Select created_at From ms_remote_key Order By created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}