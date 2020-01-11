package com.example.kotlin2.util

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.kotlin2.R

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun Context.inflate(res: Int, parent: ViewGroup? = null): View {
    return LayoutInflater.from(this).inflate(res, parent, false)
}

fun ImageView.load(url: String?) {
    if (TextUtils.isEmpty(url)) {
        Glide.with(context)
            .load(R.mipmap.ic_launcher)
            .centerCrop()
            .fitCenter()
            .into(this)

    } else {
        Glide.with(context)
            .load(url)
            .centerCrop()
            .fitCenter()
            .into(this)
    }
}