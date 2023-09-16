package com.katilijiwoadiwiyono.core.di

import android.app.Application
import androidx.room.Room
import com.katilijiwoadiwiyono.core.data.local.ImageGalleryRoom
import com.katilijiwoadiwiyono.core.utils.DatabaseUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideArticEduApi(
        @ApplicationContext application: Application
    ) = Room.databaseBuilder(application, ImageGalleryRoom::class.java, DatabaseUtil.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

    @Singleton
    @Provides
    fun provideNotifiedPrayerDao(database: ImageGalleryRoom) = database.artWorkDao()

}