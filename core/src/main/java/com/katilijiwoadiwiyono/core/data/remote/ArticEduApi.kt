package com.katilijiwoadiwiyono.core.data.remote

import com.google.gson.Gson
import com.katilijiwoadiwiyono.core.data.remote.response.ArtworkResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import javax.inject.Inject

interface ArticEduApi {

    @GET("v1/artworks?")
    suspend fun getArtwork(@Query("page") page: Int, @Query("limit") limit: Int): Response<ArtworkResponse>


    @GET("v1/artworks/search?")
    suspend fun searchArtwork(@Query("q") query: String, @Query("page") page: Int, @Query("limit") limit: Int): Response<ArtworkResponse>

    class Creator {
        @Inject
        fun articApi(
            url: String,
            httpClient: OkHttpClient,
            gsonConverterFactory: GsonConverterFactory
        ): ArticEduApi {
            val retrofit = Retrofit
                .Builder()
                .baseUrl(url)
                .addConverterFactory(gsonConverterFactory)
                .client(httpClient)
                .build()

            return retrofit.create(ArticEduApi::class.java)
        }
    }
}