package com.example.kotlin2.data.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.kotlin2.data.repository.IYoutubeRepository
import com.example.kotlin2.model.PlaylistModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YoutubeClient() : IYoutubeClient {
    val channel = "UC-JQzTHQrVA8j-tamvy66fw"
    val apiKey = "AIzaSyCWK-EoCHecYMMFAvl-DI5iegR9s1WW20Y"
    val part = "snippet,contentDetails"
    val maxResult = "50"

    override fun getPlaylists(youtubeCallback: IYoutubeRepository.OnYoutubeCallback) {
        RetrofitBuilder.getService().getYoubePlaylists(
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
                        }else {
                            Log.d("ololo", "upss, no data")
                        }
                    }else {
                        Log.d("ololo", "bad request")
                    }
                }

                override fun onFailure(call: Call<PlaylistModel>, t: Throwable) {
                    youtubeCallback.onFailure(Exception(t))
                }
            })
    }
}
