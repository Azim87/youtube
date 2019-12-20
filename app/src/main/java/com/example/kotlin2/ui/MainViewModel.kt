package com.example.kotlin2.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin2.App
import com.example.kotlin2.data.repository.IYoutubeRepository
import com.example.kotlin2.model.PlaylistModel


class MainViewModel : ViewModel() {
    private var mQuestions: MutableList<String> = mutableListOf()
    val mImages = MutableLiveData<PlaylistModel>()

    fun getImages() {
        return App.youtubeRepository.getYoutubeData(object : IYoutubeRepository.OnYoutubeCallback {
            override fun onFailure(error: Exception) {
            }

            override fun onSuccess(data: MutableLiveData<PlaylistModel>) {
                mImages.value = data.value
            }
        })
    }
}