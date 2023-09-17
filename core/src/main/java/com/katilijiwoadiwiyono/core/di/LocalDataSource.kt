package com.katilijiwoadiwiyono.core.di

import com.katilijiwoadiwiyono.core.data.local.dao.ArtWorkDao
import com.katilijiwoadiwiyono.core.data.local.dao.RemoteKeysDao
import com.katilijiwoadiwiyono.core.data.local.source.LocalDataSource
import com.katilijiwoadiwiyono.core.data.local.source.LocalDataSourceImpl
import com.katilijiwoadiwiyono.core.data.remote.ArticEduApi
import com.katilijiwoadiwiyono.core.data.remote.source.RemoteDataSource
import com.katilijiwoadiwiyono.core.data.remote.source.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalDataSource {

    @Provides
    @Singleton
    fun provideLocalDataSource(artWorkDao: ArtWorkDao, remoteKeysDao: RemoteKeysDao): LocalDataSource {
        return LocalDataSourceImpl(artWorkDao, remoteKeysDao)
    }
}