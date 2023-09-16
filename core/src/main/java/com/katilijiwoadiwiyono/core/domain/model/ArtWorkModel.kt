package com.katilijiwoadiwiyono.core.domain.model

import com.katilijiwoadiwiyono.core.data.local.entity.ArtWorkEntity
import com.katilijiwoadiwiyono.core.data.remote.response.ArtworkDataResponse
import com.katilijiwoadiwiyono.core.data.remote.response.ArtworkResponse

data class ArtWorkModel(
    val imageId: String,
    val title: String,
    val description: String,
    val imageUrl: String
) {

    companion object {
        fun mapArtWorkModel(response: ArtworkResponse.ArtworkDataResponse): ArtWorkModel {
            return ArtWorkModel(
                title = response.title ?: "",
                description = response.description ?: "",
                imageId = response.imageId ?: "",
                imageUrl = "https://www.artic.edu/iiif/2/${response.imageId}/full/200,/0/default.jpg"
            )
        }

        fun mapArtWorkModel(entity: ArtWorkEntity): ArtWorkModel {
            return ArtWorkModel(
                title = entity.title ?: "",
                description = entity.description ?: "",
                imageId = entity.imageId ?: "",
                imageUrl = "https://www.artic.edu/iiif/2/${entity.imageId}/full/200,/0/default.jpg"
            )
        }
    }

}