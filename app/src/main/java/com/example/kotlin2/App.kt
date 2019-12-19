package com.example.kotlin2

import android.app.Application
import com.example.kotlin2.data.repository.IYoutubeRepository
import com.example.kotlin2.data.repository.YoutubeRepository

class App : Application() {
    companion object {
        lateinit var instance: App
        lateinit var youtubeRepository : IYoutubeRepository
        private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        youtubeRepository = YoutubeRepository()
    }
}