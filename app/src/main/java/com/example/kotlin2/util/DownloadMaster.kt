package com.example.kotlin2.util

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import java.io.File

class DownloadMaster  {
    var file: File = File("/youtube/")

    fun downloadFile(context: Context, url: String, fileName: String?) {
        file.mkdir()
        var request: DownloadManager.Request =
            DownloadManager.Request(Uri.parse(url))
                .setTitle("$fileName") // Title of the Download Notification
                .setDescription("Downloading") // Description of the Download Notification
                .setDestinationUri(Uri.fromFile(file))
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE) // Visibility of the download Notification
                .setAllowedOverMetered(true) // Set if download is allowed on Mobile network
                .setAllowedOverRoaming(true)
                .setDestinationInExternalPublicDir(file.toString(), fileName)

        val downloadManager: DownloadManager = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }
}