package com.example.kotlin2.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlin2.R

class DetailPlaylistActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_playlist)
        title = "Detail"
    }
}
