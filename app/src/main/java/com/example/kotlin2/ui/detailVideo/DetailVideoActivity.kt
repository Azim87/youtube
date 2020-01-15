package com.example.kotlin2.ui.detailVideo

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.example.kotlin2.R
import com.example.kotlin2.model.DetailVideolModel
import com.example.kotlin2.model.YtVideo
import com.example.kotlin2.ui.detailVideo.adapter.DownloadDialogAdapter
import com.example.kotlin2.util.*
import com.google.android.exoplayer2.Player
import kotlinx.android.synthetic.main.activity_detail_video.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class DetailVideoActivity : AppCompatActivity(), CallBacks.playerCallBack {

    private val ITAG_FOR_AUDIO = 140
    private var mDetailVideoViewModel: DetailVideoViewModel? = null
    private var writePermission = false
    private var videoId: String? = null
    private var playlistId: String? = null
    private var formatsToShowList: MutableList<YtVideo>? = null
    private var selectedVideoQuality: String? = null
    private var selectedVideoExt: String? = null
    private var fileVideo: YtVideo? = null
    private var fileName: String? = null
    private lateinit var player: Player
    private lateinit var playerManager: PlayerManager
    private lateinit var dialogDownloadButton: Button
    private lateinit var dialogRecyclerView: RecyclerView
    private lateinit var dialogAdapter: DownloadDialogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_video)
        formatsToShowList = ArrayList()
        playerManager = PlayerManager.getSharedInstance(this)
        player = playerManager.playerView.player
        setupViews()
        getIntentData()
        getVideoDetailData()
        onBackClick()
    }

    private fun getIntentData() {
        videoId = intent.getStringExtra(Constants().VIDEOS_ID)
        playlistId = intent.getStringExtra(Constants().PLAYLIST_ID)
    }

    private fun subscribeToViewModel() {
        if (NetworkUtil.networkIsOnline()) {
            mDetailVideoViewModel =
                ViewModelProviders.of(this).get(DetailVideoViewModel::class.java)
            videoId?.let { mDetailVideoViewModel!!.getVideoDetail(it) }

            mDetailVideoViewModel?.mVideoDetail?.observe(
                this,
                Observer<DetailVideolModel> { videoData: DetailVideolModel ->
                    if (videoData != null) {
                        updateAdapterData(videoData)
                        updateDatabaseDetailedVideoData(videoData)
                    }
                }
            )
        } else {
            UIHelper().showToast("Check internet connection")
            getVideoDetailData()
        }
    }

    private fun getVideoDetailData() {
        CoroutineScope(Dispatchers.Main).launch {
            val videoModel = mDetailVideoViewModel?.getAllVideoDetailData()
            if (videoModel != null && !videoModel.isNullOrEmpty()) {
                getExtraDetailedPlaylistData(videoModel)
            } else {
                subscribeToViewModel()
            }
        }
    }

    private fun getExtraDetailedPlaylistData(model: List<DetailVideolModel>) {
        var detailPlaylist: DetailVideolModel? = null
        for (i in 0 until model.size) {
            for (z in 0 until model[i].items.size) {
                if (model[i].items[z].id == videoId) {
                    detailPlaylist = model[i]
                }
            }
        }

        if (detailPlaylist != null) updateAdapterData(detailPlaylist)
        else subscribeToViewModel()
    }

    private fun updateDatabaseDetailedVideoData(videoModel: DetailVideolModel) {
        mDetailVideoViewModel!!.insertAllDetailVideoData(videoModel)
    }

    private fun initDialogAdapter() {
        dialogAdapter = DownloadDialogAdapter { item: YtVideo -> downloadClickItem(item) }
        dialogRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        dialogRecyclerView.adapter = dialogAdapter
    }

    private fun downloadClickItem(item: YtVideo) {
        selectedVideoQuality = item.videoFile?.url
        selectedVideoExt = item.videoFile?.format!!.ext
        fileVideo = item
    }

    private fun updateAdapterData(list: DetailVideolModel?) {
        video_title.text = list?.items?.get(0)?.snippet?.title
        video_description.text = list?.items?.get(0)?.snippet?.description
        fileName = list?.items?.get(0)?.snippet?.title
        val link = list?.items?.get(0)?.id.toString()
        actualLink(link)
    }

    private fun checkRequestPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1024
            )
        } else {
            writePermission = true
        }
    }

    private fun setupViews() {
        download_button.setOnClickListener {
            checkRequestPermission()
            showDownloadDialog()
        }
    }

    private fun showDownloadDialog() {
        val builder = AlertDialog.Builder(this, R.style.DownloadDialog)
        val view = layoutInflater.inflate(R.layout.alert_download_dialog, null)
        builder.setView(view)
        dialogDownloadButton = view.findViewById(R.id.btn_alert_download)
        dialogRecyclerView = view.findViewById(R.id.alert_recycler_view)

        initDialogAdapter()
        dialogAdapter.updateData(formatsToShowList)
        val alert = builder.create()
        alert.show()
        downloadAction(alert)
    }

    @TargetApi(Build.VERSION_CODES.P)
    private fun downloadAction(builder: AlertDialog) {
        dialogDownloadButton.setOnClickListener {
            var downloadName = fileName!!
            downloadName = downloadName.replace("[\\\\><\"|*?%:#/]".toRegex(), "")
            var downloadIds = ""
                if (fileVideo?.videoFile != null) {
                    downloadIds += DownloadMaster().downloadFile(
                        this,
                        fileVideo?.videoFile!!.url,
                        downloadName + "." + fileVideo?.videoFile!!.format.ext)
                    downloadIds += "-"
                }
                if (fileVideo?.audioFile != null) {
                    downloadIds += DownloadMaster().downloadFile(
                        this,
                        fileVideo?.videoFile!!.url,
                        downloadName + "." + fileVideo?.videoFile!!.format.ext
                    )
                }
            builder.dismiss()
        }
    }

    @SuppressLint("StaticFieldLeak")
    private fun actualLink(link: String) = object : YouTubeExtractor(this) {
        override fun onExtractionComplete(ytFiles: SparseArray<YtFile>?, vMeta: VideoMeta) {
            formatsToShowList = mutableListOf()
            var i = 0
            var itag: Int
            if (ytFiles != null) {
                while (i < ytFiles.size()) {
                    itag = ytFiles.keyAt(i)
                    val ytFile = ytFiles.get(itag)

                    if (ytFile.format.height == -1 || ytFile.format.height >= 360) {
                        addFormatToList(ytFile, ytFiles)
                    }
                    i++
                }
            }

            (formatsToShowList)?.sortWith(Comparator { lhs, rhs -> lhs!!.height - rhs!!.height })

            try {
                val yotutubeUrl: YtVideo? = formatsToShowList?.get(formatsToShowList!!.lastIndex -1)
                if (yotutubeUrl?.videoFile?.url != null) {
                    playVideo(yotutubeUrl.videoFile?.url!!)
                }
            } catch (ex: IOException) {
                ex.printStackTrace()
                Log.d("oooooooooooo", " detail video " + ex.localizedMessage)
            }
        }
    }.extract(link, true, true)

    private fun addFormatToList(ytFile: YtFile, ytFiles: SparseArray<YtFile>) {
        val height = ytFile.format.height
        if (height != -1) {
            for (frVideo in this.formatsToShowList!!) {
                if (frVideo.height == height && (frVideo.videoFile == null || frVideo.videoFile!!.format.fps == ytFile.format.fps)) {
                    return
                }
            }
        }

        val frVideo = YtVideo()
        frVideo.height = height
        if (ytFile.format.isDashContainer) {
            if (height > 0) {
                frVideo.videoFile = ytFile
                frVideo.audioFile = ytFiles.get(ITAG_FOR_AUDIO)
            } else {
                frVideo.audioFile = ytFile
            }
        } else {
            frVideo.videoFile = ytFile
        }
        formatsToShowList!!.add(frVideo)
    }

    private fun playVideo(url: String) {
        player_view?.player = player
        PlayerManager.getSharedInstance(this).playStream(url)
        PlayerManager.getSharedInstance(this).setPlayerListener(this)
    }

    override fun onPlayingEnd() {
    }

    override fun onItemClickOnItem(albumId: Int) {
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            player_view.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            player_view.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            player_view.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            player_view.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        }
    }

    override fun onBackPressed() {
        onBackPress()
    }

    private fun onBackPress() {
        finish()
    }

    private fun onBackClick() {
        video_back_btn.setOnClickListener {
            onBackPress()
        }
    }
}
