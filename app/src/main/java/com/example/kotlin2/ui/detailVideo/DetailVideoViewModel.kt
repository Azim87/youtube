package com.example.kotlin2.ui.detailVideo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin2.application.App
import com.example.kotlin2.data.repository.IYoutubeRepository
import com.example.kotlin2.model.DetaiVideolModel
import com.example.kotlin2.util.UIHelper

class DetailVideoViewModel : ViewModel() {
    private var youtubeRepository = App.youtubeRepository
    val mVideoDetail = MutableLiveData<DetaiVideolModel>()

    fun getVideoDetail(videoId: String) {
        youtubeRepository.getVideoDetail(videoId, object : IYoutubeRepository.OnVideoDetail {
            override fun onSuccess(data: MutableLiveData<DetaiVideolModel>) {
                mVideoDetail.value = data.value
            }

            override fun onFailure(error: Exception) {
                UIHelper().showToast(error.localizedMessage)
            }
        })
    }
}