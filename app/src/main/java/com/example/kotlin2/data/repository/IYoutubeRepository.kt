package com.example.kotlin2.data.repository

import PlaylistModel
import androidx.lifecycle.MutableLiveData
import com.example.kotlin2.model.DetailModel

interface IYoutubeRepository {

    fun getYoutubeData(youtubeCallback: OnYoutubeCallback)
    fun getDetailYoutube(id: String, detailYoutubeCallback: OnDetailYoutubeCallback)

    interface OnYoutubeCallback {
        fun onSuccess(data:  MutableLiveData<PlaylistModel>)
        fun onFailure(error: Exception)
    }

    interface OnDetailYoutubeCallback {
        fun onSuccess(data:  MutableLiveData<DetailModel>)
        fun onFailure(error: Exception)
    }
}