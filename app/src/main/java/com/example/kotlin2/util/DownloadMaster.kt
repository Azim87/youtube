package com.example.kotlin2.util

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import java.io.File

class DownloadMaster  {
    var file: File = File("/youtubeParser/")

    @RequiresApi(Build.VERSION_CODES.P)
    fun downloadFile(context: Context, url: String, fileName: String?) {
        file.mkdir()
        var request: DownloadManager.Request =
            DownloadManager.Request(Uri.parse(url))
                .setTitle("$fileName")
                .setDescription("Downloading...")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)
                .setRequiresCharging(false)
                .setDestinationInExternalPublicDir(
                    file.toString(),
                    fileName
                )

        val downloadManager: DownloadManager = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }
}