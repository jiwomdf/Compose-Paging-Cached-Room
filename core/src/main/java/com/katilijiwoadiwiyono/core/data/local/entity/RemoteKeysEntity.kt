package com.katilijiwoadiwiyono.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.katilijiwoadiwiyono.core.data.remote.response.ArtworkResponse


@Entity(tableName = "ms_remote_key")
data class RemoteKeysEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "image_id")
    val imageId: String,
    val prevKey: Int?,
    val currentPage: Int,
    val nextKey: Int?,
    @ColumnInfo(name = "created_at")
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
                    imageId = it.imageId ?: "",
                    prevKey = prevKey,
                    nextKey = nextKey,
                    currentPage = page
                )
            }
        }
    }
}