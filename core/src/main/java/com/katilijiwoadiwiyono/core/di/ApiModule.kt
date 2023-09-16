package com.katilijiwoadiwiyono.core.di

import com.google.gson.Gson
import com.katilijiwoadiwiyono.core.BuildConfig
import com.katilijiwoadiwiyono.core.data.remote.ArticEduApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton
@InstallIn(SingletonComponent::class)
@Module
object ApiModule {

    @Provides
    @Singleton
    fun provideArticEduApi(okHttpClient: OkHttpClient, gson: Gson): ArticEduApi {
        return ArticEduApi.Creator().articApi(BuildConfig.BASE_URL, okHttpClient, gson)
    }

}