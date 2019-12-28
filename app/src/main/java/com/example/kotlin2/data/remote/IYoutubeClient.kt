package com.example.kotlin2.data.remote

import com.example.kotlin2.data.repository.IYoutubeRepository

interface IYoutubeClient {
    fun getPlaylists(youtubeCallback: IYoutubeRepository.OnYoutubeCallback)
    fun  getDetailedPlaylistData(id: String, detailYoutubeCallback: IYoutubeRepository.OnDetailYoutubeCallback)
}