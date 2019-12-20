@file:Suppress("DEPRECATION")

package com.example.kotlin2.util

import android.content.Context
import android.net.ConnectivityManager
import com.example.kotlin2.App

object NetworkUtil {
    fun networkIsOnline(): Boolean {
        val connectivityManager = App.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}