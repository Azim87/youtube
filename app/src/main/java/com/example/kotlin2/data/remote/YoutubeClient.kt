package com.example.kotlin2.data.remote

import com.example.kotlin2.model.PlaylistModel
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.kotlin2.data.repository.IYoutubeRepository
import com.example.kotlin2.model.DetailVideolModel
import com.example.kotlin2.model.DetailModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YoutubeClient : IYoutubeClient {
    private var youtubeBuilder = RetrofitBuilder.getService()

    //region playlist
    val channel = "UC_Fh8kvtkVPkeihBs42jGcA"
    val apiKey = "AIzaSyCWK-EoCHecYMMFAvl-DI5iegR9s1WW20Y"
    val part = "snippet,contentDetails"
    val maxResult = "50"
    //endregion

    override fun getPlaylists(youtubeCallback: IYoutubeRepository.OnYoutubeCallback) {
        youtubeBuilder.getYouTubePlaylists(
            part,
            apiKey,
            channel,
            maxResult)
            .enqueue(object : Callback<PlaylistModel> {
                val data = MutableLiveData<PlaylistModel>()
                override fun onResponse(call: Call<PlaylistModel>, response: Response<PlaylistModel>) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            data.value = response.body()
                            youtubeCallback.onSuccess(data)
                            Log.d("ololo" ," df" + response.body())
                        }
                    }
                }

                override fun onFailure(call: Call<PlaylistModel>, t: Throwable) {
                    youtubeCallback.onFailure(Exception(t))
                }
            })
    }

    override fun getDetailedPlaylistData(playlistId: String, detailYoutubeCallback: IYoutubeRepository.OnDetailYoutubeCallback) {
        youtubeBuilder.getDetailYouTubePlaylist(
            part,
            apiKey,
            playlistId,
            maxResult
        ).enqueue(object : Callback<DetailModel> {
            val data = MutableLiveData<DetailModel>()
            override fun onResponse(call: Call<DetailModel>, response: Response<DetailModel>) {
                if (response.isSuccessful){
                    if (response.body() != null){
                        data.value = response.body()
                        detailYoutubeCallback.onSuccess(data)
                    }
                } else {
                    Log.d("ololo", "not successful " + response.code())
                }
            }

            override fun onFailure(call: Call<DetailModel>, t: Throwable) {
                detailYoutubeCallback.onFailure(java.lang.Exception(t))
            }

        })
    }

    override fun getVideo(videoId: String, videoDetailYoutubeCallback: IYoutubeRepository.OnVideoDetail) {
        youtubeBuilder.getVideoDetail(
            apiKey,
            part,
            videoId
        ).enqueue(object : Callback<DetailVideolModel> {
            val data = MutableLiveData<DetailVideolModel>()
            override fun onResponse(
                call: Call<DetailVideolModel>,
                response: Response<DetailVideolModel> ) {
                if (response.isSuccessful){
                    data.value = response.body()
                    videoDetailYoutubeCallback.onSuccess(data)
                }
            }

            override fun onFailure(call: Call<DetailVideolModel>, t: Throwable) {
                videoDetailYoutubeCallback.onFailure(java.lang.Exception(t))
            }

        })

    }
}
