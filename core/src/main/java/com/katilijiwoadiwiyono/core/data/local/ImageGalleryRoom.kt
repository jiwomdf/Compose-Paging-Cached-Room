package com.katilijiwoadiwiyono.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.katilijiwoadiwiyono.core.data.local.dao.ArtWorkDao
import com.katilijiwoadiwiyono.core.data.local.dao.RemoteKeysDao
import com.katilijiwoadiwiyono.core.data.local.entity.ArtWorkEntity
import com.katilijiwoadiwiyono.core.data.local.entity.RemoteKeysEntity


@Database(version = 1, entities = [ArtWorkEntity::class, RemoteKeysEntity::class])
abstract class ImageGalleryRoom: RoomDatabase() {

    abstract fun artWorkDao(): ArtWorkDao
    abstract fun remoteKeysDao(): RemoteKeysDao

}