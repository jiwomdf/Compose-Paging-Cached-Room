package com.katilijiwoadiwiyono.core.data.remote.source

import com.katilijiwoadiwiyono.core.data.remote.response.ArtworkDataResponse
import com.katilijiwoadiwiyono.core.data.remote.response.ArtworkResponse
import retrofit2.Response

interface RemoteDataSource {
    fun getArtwork(page: Int, limit: Int): Response<ArtworkResponse>
}