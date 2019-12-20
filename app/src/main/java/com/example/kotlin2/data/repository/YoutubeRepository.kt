package com.example.kotlin2.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlin2.data.remote.YoutubeClient
import com.example.kotlin2.data.repository.IYoutubeRepository.OnYoutubeCallback
import com.example.kotlin2.model.PlaylistModel


class YoutubeRepository : IYoutubeRepository {
    private var youtubeClient: YoutubeClient? = null

    init {
        youtubeClient = YoutubeClient()
    }

    override fun getYoutubeData(youtubeCallback: OnYoutubeCallback) {

        youtubeClient?.getPlaylists(object : OnYoutubeCallback {

            override fun onSuccess(data: MutableLiveData<PlaylistModel>) {
                youtubeCallback.onSuccess(data)
            }

            override fun onFailure(error: Exception) {
                youtubeCallback.onFailure(error)
            }
        })
    }
}