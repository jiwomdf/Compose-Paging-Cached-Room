package com.katilijiwoadiwiyono.core.domain.model

import com.katilijiwoadiwiyono.core.data.local.entity.ArtWorkEntity
import com.katilijiwoadiwiyono.core.data.remote.response.ArtWorkDetailResponse
import com.katilijiwoadiwiyono.core.data.remote.response.ArtworkResponse
import com.katilijiwoadiwiyono.core.utils.StringUtil.getImageUrl

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
                ArtWorkModel(
                    id = it.id,
                    title = it.title ?: "",
                    description = it.description ?: "",
                    imageId = it.imageId ?: "",
                    imageUrl = if(!it.imageId.isNullOrEmpty()) getImageUrl(it.imageId) else ""
                )
            }
        }

        fun mapArtWorkModel(response: ArtWorkDetailResponse): ArtWorkModel {
            return response.artworkResponse.let {
                ArtWorkModel(
                    id = it.id,
                    title = it.title ?: "",
                    description = it.description ?: "",
                    imageId = it.imageId ?: "",
                    imageUrl = if(!it.imageId.isNullOrEmpty()) getImageUrl(it.imageId) else ""
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

        fun mapArtWorkModel(response: ArtworkResponse.ArtworkDataResponse): ArtWorkModel {
            return ArtWorkModel(
                id = response.id,
                title = response.title ?: "",
                description = response.description ?: "",
                imageId = response.imageId ?: "",
                imageUrl = if(!response.imageId.isNullOrEmpty()) getImageUrl(response.imageId) else ""
            )
        }
    }

}