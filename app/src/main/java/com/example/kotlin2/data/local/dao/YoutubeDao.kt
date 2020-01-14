package com.example.kotlin2.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kotlin2.model.DetailModel
import com.example.kotlin2.model.PlaylistModel

@Dao
interface YoutubeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPlaylist(items: PlaylistModel)

    @Query("SELECT * FROM play_list")
    suspend fun getAllPlaylist(): PlaylistModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllDetailPlaylist(details: DetailModel)

    @Query("SELECT * FROM playlist_detail")
    suspend fun getAllDetailPlaylist(): List<DetailModel>?
}