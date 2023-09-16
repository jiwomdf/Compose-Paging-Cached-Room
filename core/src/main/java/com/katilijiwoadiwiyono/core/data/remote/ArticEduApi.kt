package com.katilijiwoadiwiyono.core.data.remote

import com.google.gson.Gson
import com.katilijiwoadiwiyono.core.data.remote.response.ArtworkDataResponse
import com.katilijiwoadiwiyono.core.data.remote.response.ArtworkResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import retrofit2.http.POST
import retrofit2.http.Query

interface ArticEduApi {

    @POST("v1/artworks?")
    fun getArtwork(@Query("page") page: Int, @Query("limit") limit: Int): Response<ArtworkResponse>

    class Creator {
        @Inject
        fun articApi(url: String, httpClient: OkHttpClient, gson: Gson): ArticEduApi {
            val retrofit = Retrofit
                .Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build()

            return retrofit.create(ArticEduApi::class.java)
        }
    }
}