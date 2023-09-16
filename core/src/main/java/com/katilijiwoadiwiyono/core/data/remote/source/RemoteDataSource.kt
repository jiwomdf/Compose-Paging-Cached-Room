package com.katilijiwoadiwiyono.core.data.remote.source

import com.katilijiwoadiwiyono.core.data.remote.response.ArtworkResponse
import retrofit2.Response
import retrofit2.http.Query

interface RemoteDataSource {
    fun getArtwork(page: Int, limit: Int): Response<ArtworkResponse>
}