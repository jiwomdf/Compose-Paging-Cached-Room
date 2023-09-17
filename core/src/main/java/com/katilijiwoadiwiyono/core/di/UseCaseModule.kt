package com.katilijiwoadiwiyono.core.di

import com.google.gson.Gson
import com.katilijiwoadiwiyono.core.BuildConfig
import com.katilijiwoadiwiyono.core.data.remote.ArticEduApi
import com.katilijiwoadiwiyono.core.data.repository.ArtRepository
import com.katilijiwoadiwiyono.core.domain.usecase.ArtIteractor
import com.katilijiwoadiwiyono.core.domain.usecase.ArtUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {

    @Provides
    @Singleton
    fun provideArtUseCase(repository: ArtRepository): ArtUseCase {
        return ArtIteractor(repository)
    }

}