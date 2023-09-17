package com.katilijiwoadiwiyono.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.katilijiwoadiwiyono.core.data.remote.response.ArtworkResponse


@Entity(tableName = "ms_remote_key")
data class RemoteKeysEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Double,
    val prevKey: Int?,
    val currentPage: Int,
    val nextKey: Int?,
    val createdAt: Long = System.currentTimeMillis()
) {

    companion object {
        fun mapRemoteKeysEntity(
            response: List<ArtworkResponse.ArtworkDataResponse>?,
            prevKey: Int?,
            nextKey: Int?,
            page: Int
        ): List<RemoteKeysEntity>? {
            return response?.map {
                RemoteKeysEntity(
                    id = it.id,
                    prevKey = prevKey,
                    nextKey = nextKey,
                    currentPage = page
                )
            }
        }
    }
}