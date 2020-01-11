package com.example.kotlin2.application

import android.app.Application
import androidx.room.Room
import com.example.kotlin2.data.local.db.YoutubeDataBase
import com.example.kotlin2.data.repository.IYoutubeRepository
import com.example.kotlin2.data.repository.YoutubeRepository

class App : Application() {
    companion object {
        lateinit var instance: App
        lateinit var youtubeRepository : IYoutubeRepository
        lateinit var database: YoutubeDataBase
        private const val DATABASE: String = "youtubedb"
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        youtubeRepository = YoutubeRepository()
        database = Room.databaseBuilder(this, YoutubeDataBase::class.java,
            DATABASE
        ).build()
    }

    fun getInstance(): App {
        return instance
    }

    fun getDataBase(): YoutubeDataBase {
        return database
    }
}