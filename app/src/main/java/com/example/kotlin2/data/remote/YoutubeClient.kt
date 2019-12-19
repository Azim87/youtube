package com.example.kotlin2.data.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.kotlin2.data.repository.IYoutubeRepository
import com.example.kotlin2.model.PlaylistModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YoutubeClient() : IYoutubeClient {

    private val BASE_URL = "https://www.googleapis.com"
    val channel = "UC_IfiZu3VkesO3L58L9WPhA"
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
                override fun onResponse(
                    call: Call<PlaylistModel>,
                    response: Response<PlaylistModel>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            data.value = response.body()
                            youtubeCallback.onSuccess(data)
                            Log.d("ololo", "ssssss " + data)
                        }
                    }
                }

                override fun onFailure(call: Call<PlaylistModel>, t: Throwable) {
                    youtubeCallback.onFailure(t.localizedMessage)
                    Log.d("___________Sdsdsd", "asdasdsadsad")
                }
            })

    }
}
