package com.example.kotlin2.ui.detailVideo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin2.application.App
import com.example.kotlin2.data.repository.IYoutubeRepository
import com.example.kotlin2.model.DetailVideolModel
import com.example.kotlin2.util.UIHelper
import kotlinx.coroutines.launch

class DetailVideoViewModel : ViewModel() {
    private var youtubeRepository = App.youtubeRepository
    private val db = App().getInstance().getDataBase()
    val mVideoDetail = MutableLiveData<DetailVideolModel>()

    fun getVideoDetail(videoId: String) {
        youtubeRepository.getVideoDetail(videoId, object : IYoutubeRepository.OnVideoDetail {
            override fun onSuccess(data: MutableLiveData<DetailVideolModel>) {
                mVideoDetail.value = data.value
            }

            override fun onFailure(error: Exception) {
                UIHelper().showToast(error.localizedMessage)
            }
        })
    }

    fun insertAllDetailVideoData(videoData: DetailVideolModel) {
        viewModelScope.launch {
            db.ytVideoDao().insertAllDetailVideo(videoData)
        }
    }

    suspend fun getAllVideoDetailData(): List<DetailVideolModel>? {
        return db.ytVideoDao().getAllDetailVideo()
    }
}