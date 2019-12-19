package com.example.kotlin2.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.kotlin2.data.remote.IYoutubeClient
import com.example.kotlin2.data.remote.YoutubeClient
import com.example.kotlin2.data.repository.IYoutubeRepository.OnYoutubeCallback
import com.example.kotlin2.model.PlaylistModel


class YoutubeRepository : IYoutubeRepository {



    override fun getYoutubeData(youtubeCallback: OnYoutubeCallback) {
        val youtubeClient: YoutubeClient? = null
        youtubeClient?.getPlaylists(object : OnYoutubeCallback {

            override fun onSuccess(data: MutableLiveData<PlaylistModel>) {
                youtubeCallback.onSuccess(data)
            }

            override fun onFailure(error: String) {
                youtubeCallback.onFailure(error)
            }
        })
    }
}