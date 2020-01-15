package com.example.kotlin2.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kotlin2.model.DetailVideolModel
import com.example.kotlin2.model.DetailModel
import com.example.kotlin2.model.PlaylistModel

@Dao
interface YoutubeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPlaylist(items: PlaylistModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllDetailPlaylist(details: DetailModel)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllDetailVideo(detailVideo: DetailVideolModel)

    @Query("SELECT * FROM play_list")
    suspend fun getAllPlaylist(): PlaylistModel

    //insert detail playlist
    @Query("SELECT * FROM playlist_detail")
    suspend fun getAllDetailPlaylist(): List<DetailModel>?

    @Query("SELECT * FROM detail_video")
    suspend fun getAllDetailVideo(): List<DetailVideolModel>?
}