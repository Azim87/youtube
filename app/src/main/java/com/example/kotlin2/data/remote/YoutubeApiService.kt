package com.example.kotlin2.data.remote

import PlaylistModel
import com.example.kotlin2.model.DetailModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeApiService {
    @GET("youtube/v3/playlists")
    fun getYouTubePlaylists(
        @Query("part") part: String,
        @Query("key") apiKey: String,
        @Query("channelId") channelId: String,
        @Query("maxResults") maxResults: String
    ): Call<PlaylistModel>

    @GET("/youtube/v3/playlistItems")
    fun getDetailYouTubePlaylist(
        @Query("part") part: String,
        @Query("key") apiKey: String,
        @Query("playlistId") playlistId: String,
        @Query("maxResults") maxResults: String
    ): Call<DetailModel>
}


