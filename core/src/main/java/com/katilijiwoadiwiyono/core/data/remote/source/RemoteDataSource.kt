package com.katilijiwoadiwiyono.core.data.remote.source

import com.katilijiwoadiwiyono.core.data.remote.response.ArtWorkDetailResponse
import com.katilijiwoadiwiyono.core.data.remote.response.ArtworkResponse
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getArtwork(page: Int, limit: Int): Response<ArtworkResponse>
    suspend fun searchArtworks(query: String, page: Int, limit: Int): Response<ArtworkResponse>
    suspend fun getArtworkById(id: Double): Response<ArtWorkDetailResponse>
}