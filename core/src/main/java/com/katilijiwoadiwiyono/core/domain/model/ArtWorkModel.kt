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