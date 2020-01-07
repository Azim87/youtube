package com.example.kotlin2.data.repository

import PlaylistModel
import androidx.lifecycle.MutableLiveData
import com.example.kotlin2.model.DetaiVideolModel
import com.example.kotlin2.model.DetailModel

interface IYoutubeRepository {

    fun getYoutubeData(youtubeCallback: OnYoutubeCallback)
    fun getDetailYoutube(id: String, detailYoutubeCallback: OnDetailYoutubeCallback)
    fun getVideoDetail(videoId: String, videoDetail: OnVideoDetail)

    interface OnYoutubeCallback {
        fun onSuccess(data:  MutableLiveData<PlaylistModel>)
        fun onFailure(error: Exception)
    }

    interface OnDetailYoutubeCallback {
        fun onSuccess(data:  MutableLiveData<DetailModel>)
        fun onFailure(error: Exception)
    }

    interface OnVideoDetail{
        fun onSuccess(data:  MutableLiveData<DetaiVideolModel>)
        fun onFailure(error: Exception)
    }
}