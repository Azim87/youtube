package com.example.kotlin2.ui.detailPlaylist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin2.application.App
import com.example.kotlin2.data.repository.IYoutubeRepository
import com.example.kotlin2.model.DetailModel
import com.example.kotlin2.util.UIHelper
import kotlinx.coroutines.launch

class DetailPlaylistViewModel : ViewModel() {
    private var youtubeRepository = App.youtubeRepository
    private val db = App().getInstance().getDataBase()
    val mDetailPlaylist = MutableLiveData<DetailModel>()

    fun getParsedData(id: String) {
        youtubeRepository.getDetailYoutube(id, object : IYoutubeRepository.OnDetailYoutubeCallback {
            override fun onSuccess(data: MutableLiveData<DetailModel>) {
                mDetailPlaylist.value = data.value
            }

            override fun onFailure(error: Exception) {
                UIHelper().showToast(error.message.toString())
                Log.d("ololo", " error" + error.localizedMessage)
            }
        })
    }

    suspend fun getDetailedPlaylistData(): List<DetailModel>? {
        return db.ytVideoDao().getAllDetailPlaylist()
    }

    fun insertAllDetailPlaylistData(detailModel: DetailModel) {
        viewModelScope.launch {
            db.ytVideoDao().insertAllDetailPlaylist(detailModel)
        }
    }
}