package com.example.kotlin2.ui.detailPlaylist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin2.R
import com.example.kotlin2.model.DetailModel
import com.example.kotlin2.model.ItemsItem
import com.example.kotlin2.ui.detailPlaylist.adapter.PlaylistAdapter
import com.example.kotlin2.ui.detailVideo.DetailVideoActivity
import com.example.kotlin2.util.Constants
import com.example.kotlin2.util.NetworkUtil
import com.example.kotlin2.util.UIHelper
import kotlinx.android.synthetic.main.activity_detail_playlist.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        mViewModel = ViewModelProviders.of(this).get(DetailPlaylistViewModel::class.java)
        initRecycler()
        getIntentData()
        getDetailPlaylistData()
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

    private fun getDetailPlaylistData() {
        CoroutineScope(Dispatchers.Main).launch {
            val model = mViewModel.getDetailedPlaylistData()
            if (model != null && !model.isNullOrEmpty()) {
               getExtraDetailedPlaylistData(model)
            } else {
                subscribeToViewModel()
            }
        }
    }

    private fun getExtraDetailedPlaylistData(model: List<DetailModel>) {
        var detailPlaylist: DetailModel? = null
        for (i in 0 until model.size) {
            for (z in 0 until model[i].items.size) {
                if (model[i].items[z].snippet.playlistId == id) {
                    detailPlaylist = model[i]
                }
            }
        }

        if (detailPlaylist != null) updateAdapterData(detailPlaylist)
         else subscribeToViewModel()
    }

    private fun subscribeToViewModel() {
        if (NetworkUtil.networkIsOnline()) {
            id?.let { mViewModel.getParsedData(it) }
            mViewModel.mDetailPlaylist.observe(
                this,
                Observer<DetailModel> { data: DetailModel ->
                    updateAdapterData(data)
                    updateDatabaseDetailedPlaylistData(data)
                }
            )
        } else {
            UIHelper().showToast("Check internet connection")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateAdapterData(list: DetailModel?) {
        tv_description.text = list!!.items[0].snippet.description
        tv_title.text = titlee
        playlist_total_video.text = list.pageInfo.totalResults.toString() + " video series"
        mPlaylistAdapter.submitList(list.items)
    }

    private fun updateDatabaseDetailedPlaylistData(video: DetailModel) {
        mViewModel.insertAllDetailPlaylistData(video)
    }

    private fun backToMain() {
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
