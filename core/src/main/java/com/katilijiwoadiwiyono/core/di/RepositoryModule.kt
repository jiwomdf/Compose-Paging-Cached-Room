package com.katilijiwoadiwiyono.core.di

import androidx.room.Database
import com.google.gson.Gson
import com.katilijiwoadiwiyono.core.BuildConfig
import com.katilijiwoadiwiyono.core.data.local.ImageGalleryRoom
import com.katilijiwoadiwiyono.core.data.local.source.LocalDataSource
import com.katilijiwoadiwiyono.core.data.remote.ArticEduApi
import com.katilijiwoadiwiyono.core.data.remote.source.RemoteDataSource
import com.katilijiwoadiwiyono.core.data.repository.ArtRepository
import com.katilijiwoadiwiyono.core.data.repository.ArtRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideArtRepository(
        database: ImageGalleryRoom,
        remoteDataSource: RemoteDataSource,
    ): ArtRepository {
        return ArtRepositoryImpl(database, remoteDataSource)
    }
}