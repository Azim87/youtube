package com.example.kotlin2.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin2.R
import com.example.kotlin2.model.ItemsItem
import com.example.kotlin2.model.PlaylistModel
import com.example.kotlin2.ui.detail.DetailPlaylistActivity
import com.example.kotlin2.ui.main.recycler.SimpleAdapter
import com.example.kotlin2.util.NetworkUtil
import com.example.kotlin2.util.UIHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val DATA_ID: String = "id"
    private val DATA_TITLE: String = "title"
    private val DATA_CHANNEL: String = "channelTitle"
    private val DATA_ETAG: String = "etag"
    private lateinit var mViewModel: MainViewModel
    private lateinit var mAdapter: SimpleAdapter
    private lateinit var layout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
        initViewModel()
        fetchData()
        checkInternet()
    }

    private fun initRecyclerView() {
        layout = findViewById(R.id.layout_connection)
        main_recycler_view.apply {
            mAdapter = SimpleAdapter { item: ItemsItem ->
                clickItem(item)
            }
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mAdapter
        }
    }

    private fun clickItem(item: ItemsItem) {
        val intent = Intent(this, DetailPlaylistActivity::class.java)
        intent.putExtra(DATA_ID, item.id)
        intent.putExtra(DATA_TITLE, item.snippet.title)
        intent.putExtra(DATA_CHANNEL, item.snippet.channelId)
        intent.putExtra(DATA_ETAG, item.etag)
        startActivity(intent)
    }

    private fun initViewModel() {
        mViewModel = ViewModelProviders.of(this@MainActivity).get(MainViewModel::class.java)
    }

    private fun updateAdapterData(list: PlaylistModel?) {
        val data = list?.items
        mAdapter.submitList(data)
    }

    private fun fetchData() {
        mViewModel.getImages()
        mViewModel.mImages.observe(this, Observer<PlaylistModel> {
            this.updateAdapterData(it)
        })
    }

    fun refresh(view: View) {
        checkInternet()
    }

    fun checkInternet() {
        if (!NetworkUtil.networkIsOnline()) {
            UIHelper().showToast("нет доспута в интернет")
            layout.visibility = View.VISIBLE

        } else {
            fetchData()
            layout.visibility = View.INVISIBLE
        }
    }
}
