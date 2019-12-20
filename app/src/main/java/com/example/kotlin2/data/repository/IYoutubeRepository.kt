package com.example.kotlin2.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlin2.model.PlaylistModel

interface IYoutubeRepository {

    fun getYoutubeData(youtubeCallback: OnYoutubeCallback)
    interface OnYoutubeCallback {

        fun onSuccess(data:  MutableLiveData<PlaylistModel>)

        fun onFailure(error: Exception)
    }
}