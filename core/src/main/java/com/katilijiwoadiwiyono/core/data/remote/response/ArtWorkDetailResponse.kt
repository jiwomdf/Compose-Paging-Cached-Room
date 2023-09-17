package com.katilijiwoadiwiyono.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class ArtWorkDetailResponse(
    @SerializedName("data") val artworkResponse: ArtworkResponse.ArtworkDataResponse
)