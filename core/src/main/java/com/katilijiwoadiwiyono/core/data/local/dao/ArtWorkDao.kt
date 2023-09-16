package com.katilijiwoadiwiyono.core.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.katilijiwoadiwiyono.core.data.local.entity.ArtWorkEntity


@Dao
interface ArtWorkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtWork(msAyah: List<ArtWorkEntity>)

    @Query("SELECT * FROM ms_art_work")
    fun getArtWork(): PagingSource<Int, ArtWorkEntity>

    @Query("Delete From ms_art_work")
    suspend fun clearAllArtWork()
}