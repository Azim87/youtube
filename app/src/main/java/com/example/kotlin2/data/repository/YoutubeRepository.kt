package com.example.kotlin2.data.repository

import PlaylistModel
import androidx.lifecycle.MutableLiveData
import com.example.kotlin2.data.remote.YoutubeClient
import com.example.kotlin2.data.repository.IYoutubeRepository.OnYoutubeCallback
import com.example.kotlin2.model.DetailModel

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

    override fun getDetailYoutube(id: String, detailYoutubeCallback: IYoutubeRepository.OnDetailYoutubeCallback) {
        youtubeClient?.getDetailedPlaylistData(id, object : IYoutubeRepository.OnDetailYoutubeCallback {
            override fun onSuccess(data: MutableLiveData<DetailModel>) {
               detailYoutubeCallback.onSuccess(data)
            }

            override fun onFailure(error: Exception) {
                detailYoutubeCallback.onFailure(java.lang.Exception(error))
            }
        })

    }
}