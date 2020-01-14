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
import com.example.kotlin2.ui.detailPlaylist.DetailPlaylistActivity
import com.example.kotlin2.ui.main.adapter.SimpleAdapter
import com.example.kotlin2.util.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var mViewModel: MainViewModel
    private lateinit var mAdapter: SimpleAdapter
    private lateinit var network_container: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mViewModel = ViewModelProviders.of(this@MainActivity).get(MainViewModel::class.java)
        initRecyclerView()
        getDataFromDatabase()
    }

    fun refresh(view: View) {
        fetchData()
    }

    private fun initRecyclerView() {
        network_container = findViewById(R.id.layout_connection)
        main_recycler_view.apply {
            mAdapter = SimpleAdapter(this@MainActivity::clickItem)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mAdapter
        }
    }

    private fun clickItem(item: ItemsItem) {
        val intent = Intent(this, DetailPlaylistActivity::class.java)
        intent.putExtra(Constants().DATA_ID, item.id)
        intent.putExtra(Constants().DATA_TITLE, item.snippet.title)
        intent.putExtra(Constants().DATA_CHANNEL, item.snippet.channelId)
        intent.putExtra(Constants().DATA_ETAG, item.snippet.description)
        startActivity(intent)
    }

    private fun fetchData() {
        if (NetworkUtil.networkIsOnline()) {
            network_container.gone()
            mViewModel.getImages()
            mViewModel.mImages.observe(this, Observer<PlaylistModel> {
                val model: PlaylistModel? = it
                this.updateAdapterData(model)
                if (model != null) {
                    updateDatabasePlaylist(model)
                    getDataFromDatabase()
                }
            })

        } else {
            network_container.visible()
            UIHelper().showToast(getString(R.string.internet_connection))
        }
    }

    private fun updateAdapterData(list: PlaylistModel?) {
        val data = list?.items
        mAdapter.submitList(data)
    }

    private fun updateDatabasePlaylist(model: PlaylistModel) {
        model.let { mViewModel.insertPlaylistData(it) }
    }

    private fun getDataFromDatabase() {
        CoroutineScope(Dispatchers.Main).launch {
            val model = mViewModel.getDataFromDb()
            if (model != null || model.items.isNullOrEmpty()) {
                updateAdapterData(model)
                fetchNewPlaylistData()

            } else {
                fetchData()
            }
        }
    }

    private fun fetchNewPlaylistData() {
        mViewModel.getImages()
        mViewModel.mImages.observe(
            this,
            Observer<PlaylistModel> { data ->
                when {
                    data != null -> {
                        updateDatabasePlaylist(data)
                        updateAdapterData(data)
                    }
                }
            }
        )}
}