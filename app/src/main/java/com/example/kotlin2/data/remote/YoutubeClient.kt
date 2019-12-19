package com.example.kotlin2.data.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.kotlin2.data.repository.IYoutubeRepository
import com.example.kotlin2.model.PlaylistModel
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

abstract class YoutubeClient() : IYoutubeClient {


    private val BASE_URL = "https://www.googleapis.com"
    val channel = "UC_IfiZu3VkesO3L58L9WPhA"
    val apiKey = "AIzaSyCWK-EoCHecYMMFAvl-DI5iegR9s1WW20Y"
    val part = "snippet,contentDetails"
    val maxResult = "50"

    private val client = OkHttpClient().newBuilder()
        .connectTimeout(50, TimeUnit.SECONDS)
        .readTimeout(50, TimeUnit.SECONDS)
        .writeTimeout(50, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    override fun getPlaylists(youtubeCallback: IYoutubeRepository.OnYoutubeCallback) {
        val youtubeApiService = retrofit.create(YoutubeApiService::class.java)
        youtubeApiService.getYoubePlaylists(
            channel,
            apiKey,
            part,
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
                            Log.d("ololo", " " + data)
                        }
                    }
                }

                override fun onFailure(call: Call<PlaylistModel>, t: Throwable) {
                    youtubeCallback.onFailure(t.localizedMessage)
                }
            })

    }
}
