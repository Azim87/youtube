package com.example.kotlin2.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.kotlin2.R
import kotlinx.android.synthetic.main.activity_detail_playlist.*

class DetailPlaylistActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_playlist)
        title = "Detail"
        getIntentData()
    }

    private fun getIntentData(){
        val title : String = intent.getStringExtra("title")
        tv_title.text = title
        Log.d("ololo", "detail $title")

    }
}
