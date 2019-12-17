package com.example.kotlin2.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin2.R
import com.example.kotlin2.ui.recycler.SimpleAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var mViewModel: MainViewModel
    private lateinit var mAdapter: SimpleAdapter

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        initRecyclerView()
        onBackPress()
        onForwardPressed()
    }

    private fun initRecyclerView() {
        main_recycler_view.apply {
            mAdapter = SimpleAdapter()
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, false)
            adapter = mAdapter
        }
        main_recycler_view.setOnTouchListener { v, event -> v?.onTouchEvent(event) ?: true }
        main_recycler_view.isHorizontalScrollBarEnabled = false
    }

    @SuppressLint("RestrictedApi")
    private fun initViewModel() {
        mViewModel = ViewModelProviders.of(this@MainActivity).get(MainViewModel::class.java)
        mViewModel.mImages.observe(this, Observer<List<String>> { images ->
            mAdapter.submitList(images)
            Log.e("ololo", "" + images)

        })
        mViewModel.currentQuestionPosition.observe(this, Observer { pos ->
            main_recycler_view.smoothScrollToPosition(pos)

            if (pos == 0) {
                fab_back.visibility = View.INVISIBLE
            } else if (pos == mAdapter.itemCount) {
                fab_forward.visibility = View.INVISIBLE
            } else {
                fab_back.visibility = View.VISIBLE
                fab_forward.visibility = View.VISIBLE
            }
        })

        mViewModel.getImages()
    }

    private fun onForwardPressed() {
        fab_forward.setOnClickListener {
            mViewModel.forwardPressed()
        }
    }

    private fun onBackPress() {
        fab_back.setOnClickListener {
            mViewModel.backPressed()
        }
    }
}
