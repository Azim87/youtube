package com.example.kotlin2.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MainViewModel : ViewModel() {

    private var mQuestions: MutableList<String> = mutableListOf()
    val mImages = MutableLiveData<List<String>>()
    var currentQuestionPosition = MutableLiveData<Int>()

    fun getImages() {
        mImages.value = mQuestions
        currentQuestionPosition.value = 0

        mQuestions.add("https://tinyjpg.com/images/social/website.jpg")
        mQuestions.add("https://tinyjpg.com/images/social/website.jpg")
        mQuestions.add("https://tinyjpg.com/images/social/website.jpg")
        mQuestions.add("https://tinyjpg.com/images/social/website.jpg")
        mQuestions.add("https://tinyjpg.com/images/social/website.jpg")
        mQuestions.add("https://tinyjpg.com/images/social/website.jpg")
    }

    fun backPressed() {
        currentQuestionPosition.value = currentQuestionPosition.value?.minus(1)
    }

    fun forwardPressed() {
        currentQuestionPosition.value = currentQuestionPosition.value?.plus(1)
    }
}