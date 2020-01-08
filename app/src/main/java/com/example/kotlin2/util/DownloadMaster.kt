package com.example.kotlin2.util

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.opengl.Visibility
import android.os.Build
import java.io.File

class DownloadMaster  {
    var file: File = File("/youtube/")

    fun downloadFile(context: Context, url: String, fileName: String?) {
        file.mkdir()
        var request: DownloadManager.Request =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                DownloadManager.Request(Uri.parse(url))
                    .setTitle("$fileName") // Title of the Download Notification
                    .setDescription("Downloading...") // Description of the Download Notification
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE) // Visibility of the download Notification
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setAllowedOverMetered(true) // Set if download is allowed on Mobile network
                    .setAllowedOverRoaming(true)
                    .setRequiresCharging(false)
                    .setDestinationInExternalPublicDir(file.toString(), fileName)
            } else {
                TODO("VERSION.SDK_INT < N")
            }

        val downloadManager: DownloadManager = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }
}