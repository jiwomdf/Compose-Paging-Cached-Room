package com.katilijiwoadiwiyono.core.domain.model

import com.katilijiwoadiwiyono.core.data.local.entity.ArtWorkEntity
import com.katilijiwoadiwiyono.core.data.remote.response.ArtworkResponse

data class ArtWorkModel(
    val id: Double,
    val imageId: String,
    val title: String,
    val description: String,
    val imageUrl: String
) {

    companion object {
        fun mapArtWorkModel(response: ArtworkResponse): List<ArtWorkModel> {
            return response.artworkResponse.map {
                val imageUrl = if(!it.imageId.isNullOrEmpty()) {
                    "https://www.artic.edu/iiif/2/${it.imageId}/full/100,/0/default.jpg"
                } else {
                    ""
                }
                ArtWorkModel(
                    id = it.id,
                    title = it.title ?: "",
                    description = it.description ?: "",
                    imageId = it.imageId ?: "",
                    imageUrl = imageUrl
                )
            }
        }

        fun mapArtWorkModel(entity: ArtWorkEntity): ArtWorkModel {
            return ArtWorkModel(
                id = entity.id,
                title = entity.title,
                description = entity.description,
                imageId = entity.imageId,
                imageUrl = entity.imageUrl
            )
        }
    }

}