package com.example.kotlin2.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kotlin2.data.local.dao.YoutubeDao
import com.example.kotlin2.model.PlaylistModel

@Database(
    entities = [PlaylistModel::class],
    version = 1,
    exportSchema = false
)
abstract class YoutubeDataBase: RoomDatabase() {

    abstract fun ytVideoDao(): YoutubeDao

}