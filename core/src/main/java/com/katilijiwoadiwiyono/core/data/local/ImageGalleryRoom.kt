package com.katilijiwoadiwiyono.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.katilijiwoadiwiyono.core.data.local.dao.ArtWorkDao
import com.katilijiwoadiwiyono.core.data.local.entity.ArtWorkEntity


@Database(version = 1, entities = [ArtWorkEntity::class])
abstract class ImageGalleryRoom: RoomDatabase() {

    abstract fun artWorkDao(): ArtWorkDao

}