package com.example.kotlin2.util

import android.widget.Toast
import com.example.kotlin2.application.App

class UIHelper {
    fun showToast(message: String) {
        Toast.makeText(App.instance, message, Toast.LENGTH_LONG).show()
    }
}