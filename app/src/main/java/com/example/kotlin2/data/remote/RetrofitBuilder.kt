package com.example.kotlin2.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {

    private val BASE_URL = "https://www.googleapis.com"
    private var client: OkHttpClient? = null
    private var retrofit: YoutubeApiService? = null

    public fun getService(): YoutubeApiService {
        if (retrofit == null)
            retrofit = buildRetrofit()
        return retrofit!!
    }

    private fun getClient(): OkHttpClient? {
        if (client == null)
            client = client()
        return client!!
    }

    private fun client(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .connectTimeout(50, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)
            .writeTimeout(50, TimeUnit.SECONDS)
            .build()
    }

    private fun buildRetrofit(): YoutubeApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(getClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(YoutubeApiService::class.java)
}