package com.katilijiwoadiwiyono.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.katilijiwoadiwiyono.core.data.remote.response.ArtworkResponse
import com.katilijiwoadiwiyono.core.domain.model.ArtWorkModel

@Entity(tableName = "ms_art_work")
data class ArtWorkEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "image_id")
    val imageId: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val page: Int
) {
    companion object {
        fun mapArtWorkModel(response: List<ArtworkResponse.ArtworkDataResponse>): List<ArtWorkEntity> {
            return response.map {
                ArtWorkEntity(
                    title = it.title ?: "",
                    description = it.description ?: "",
                    imageId = it.imageId ?: "",
                    imageUrl = "https://www.artic.edu/iiif/2/${it.imageId}/full/200,/0/default.jpg"
                )
            }
        }
    }
}