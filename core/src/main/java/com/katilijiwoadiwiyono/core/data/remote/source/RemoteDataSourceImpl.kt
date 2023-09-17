package com.katilijiwoadiwiyono.core.data.remote.source

import com.katilijiwoadiwiyono.core.data.remote.ArticEduApi
import com.katilijiwoadiwiyono.core.data.remote.response.ArtworkResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val articEduApi: ArticEduApi
): RemoteDataSource {

    override suspend fun getArtwork(page: Int, limit: Int): Response<ArtworkResponse> {
        return articEduApi.getArtwork(page, limit)
    }
}