package com.katilijiwoadiwiyono.core.di

import com.google.gson.Gson
import com.katilijiwoadiwiyono.core.BuildConfig
import com.katilijiwoadiwiyono.core.data.remote.ArticEduApi
import com.katilijiwoadiwiyono.core.data.remote.source.RemoteDataSource
import com.katilijiwoadiwiyono.core.data.remote.source.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object RemoteDataSourceModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(articEduApi: ArticEduApi): RemoteDataSource {
        return RemoteDataSourceImpl(articEduApi)
    }
}