package com.example.kotlin2.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin2.App
import com.example.kotlin2.data.repository.IYoutubeRepository
import com.example.kotlin2.model.PlaylistModel


class MainViewModel : ViewModel() {
    val mImages = MutableLiveData<PlaylistModel>()

    fun getImages() {
        App.youtubeRepository.getYoutubeData(object : IYoutubeRepository.OnYoutubeCallback {
            override fun onSuccess(data: MutableLiveData<PlaylistModel>) {
                mImages.value = data.value
            }

            override fun onFailure(error: Exception) {
                Log.d("ololo", "EROOR $error" )
            }
        })
    }
}