package com.example.kotlin2.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin2.application.App
import com.example.kotlin2.data.repository.IYoutubeRepository
import com.example.kotlin2.model.PlaylistModel
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {
    val mImages = MutableLiveData<PlaylistModel>()
    private val db = App().getInstance().getDataBase()
    private var youtubeRepository = App.youtubeRepository

    fun getImages() {
        youtubeRepository.getYoutubeData(object : IYoutubeRepository.OnYoutubeCallback {
            override fun onSuccess(data: MutableLiveData<PlaylistModel>) {
                mImages.value = data.value
            }

            override fun onFailure(error: Exception) {
                Log.d("ololo", "EROOR $error" )
            }
        })
    }

    fun insertPlaylistData(model: PlaylistModel) {
       viewModelScope.launch {
         db.ytVideoDao().insertAllPlaylist(model)
       }
    }

    suspend fun getDataFromDb(): PlaylistModel{
        return db.ytVideoDao().getAllPlaylist()
    }
}