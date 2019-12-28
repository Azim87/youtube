package com.example.kotlin2.ui.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin2.App
import com.example.kotlin2.data.repository.IYoutubeRepository
import com.example.kotlin2.model.DetailModel

class DetailPlaylistViewModel : ViewModel() {
    private var youtubeRepository = App.youtubeRepository
    val mDetailPlaylist = MutableLiveData<DetailModel>()

    fun getParsedData(id: String) {
        youtubeRepository.getDetailYoutube(id, object : IYoutubeRepository.OnDetailYoutubeCallback {
            override fun onSuccess(data: MutableLiveData<DetailModel>) {
                mDetailPlaylist.value = data.value
                Log.d("ololo", "detail vm" + id)
            }

            override fun onFailure(error: Exception) {

            }

        })
    }
}