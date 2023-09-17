package com.katilijiwoadiwiyono.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.katilijiwoadiwiyono.core.data.remote.response.ArtworkResponse

@Entity(tableName = "ms_art_work")
data class ArtWorkEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Double,
    val imageId: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val page: Int
) {
    companion object {
        fun mapArtWorkModel(
            response: List<ArtworkResponse.ArtworkDataResponse>,
            page: Int
        ): List<ArtWorkEntity> {
            return response.map {
                val imageUrl = if(!it.imageId.isNullOrEmpty()) {
                    "https://www.artic.edu/iiif/2/${it.imageId}/full/100,/0/default.jpg"
                } else {
                    ""
                }

                ArtWorkEntity(
                    id = it.id,
                    title = it.title ?: "",
                    description = it.description ?: "",
                    imageId = it.imageId ?: "",
                    imageUrl = imageUrl,
                    page = page
                )
            }
        }
    }
}