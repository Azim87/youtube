package com.example.kotlin2.ui.detailVideo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlin2.R

class DetailVideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_video)
        title = "detailVideo"
    }
}
