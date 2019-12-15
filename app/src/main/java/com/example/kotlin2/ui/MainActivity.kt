package com.example.kotlin2.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin2.R
import com.example.kotlin2.ui.recycler.SimpleAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mAdapter: SimpleAdapter
    private var list: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
        sendToAdapter()
    }

    private fun initRecyclerView() {
        main_recycler_view.apply {
            mAdapter = SimpleAdapter()
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mAdapter
        }
    }

    private fun sendToAdapter() {
        list.add("hello")
        list.add("hello")
        list.add("hello")
        list.add("hello")
        mAdapter.submitList(list)
    }
}
