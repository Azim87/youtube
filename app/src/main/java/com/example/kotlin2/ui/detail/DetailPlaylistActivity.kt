package com.example.kotlin2.ui.detail

import ItemsItem
import android.annotation.SuppressLint
import android.content.ContentProvider
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore.Audio.Playlists.Members.PLAYLIST_ID
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin2.R
import com.example.kotlin2.model.DetailModel
import com.example.kotlin2.ui.detail.recycler.PlaylistAdapter
import com.example.kotlin2.ui.detailVideo.DetailVideoActivity
import com.example.kotlin2.ui.main.MainActivity
import com.example.kotlin2.util.Constants
import kotlinx.android.synthetic.main.activity_detail_playlist.*

class DetailPlaylistActivity : AppCompatActivity() {
    private lateinit var mPlaylistAdapter: PlaylistAdapter
    private lateinit var mViewModel: DetailPlaylistViewModel
    private var id: String? = null
    private var titlee: String? = null
    private var description: String? = null
    private var channelId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_playlist)
        title = "detailPlaylist"
        getIntentData()
        initRecycler()
        subscribeToViewModel()
        onBackClick()
    }

    private fun getIntentData(){
        id = intent.getStringExtra(Constants().DATA_ID)
        titlee = intent?.getStringExtra(Constants().DATA_TITLE)
        channelId = intent?.getStringExtra(Constants().DATA_CHANNEL)
        description = intent?.getStringExtra(Constants().DATA_ETAG)

    }

    private fun initRecycler() {
        recycler_view.apply {
            mPlaylistAdapter = PlaylistAdapter { item: ItemsItem -> (click(item)) }
            adapter = mPlaylistAdapter
            layoutManager = LinearLayoutManager(this@DetailPlaylistActivity)
        }
    }

    private fun click(item: ItemsItem) {
        val intent = Intent(this, DetailVideoActivity::class.java)
        intent.putExtra(Constants().PLAYLIST_ID, id)
        intent.putExtra(Constants().VIDEOS_ID, item.snippet.resourceId.videoId)
        startActivity(intent)
    }

    private fun subscribeToViewModel() {
        mViewModel = ViewModelProviders.of(this).get(DetailPlaylistViewModel::class.java)
        id?.let { mViewModel.getParsedData(it) }
        mViewModel.mDetailPlaylist.observe(this, Observer<DetailModel> {
            this.updateAdapterData(it)
        })
    }

    @SuppressLint("SetTextI18n")
    private fun updateAdapterData(list: DetailModel?) {
        val data = list!!.items
        tv_description.text = list.items[0].snippet.description
        tv_title.text = titlee
        playlist_total_video.text = list.pageInfo.totalResults.toString() + " video series"
        mPlaylistAdapter.submitList(data)
    }

    private fun backToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onBackPressed() {
        backToMain()
    }

    private fun onBackClick() {
        detail_back_btn.setOnClickListener {
            backToMain()
        }
    }
}
