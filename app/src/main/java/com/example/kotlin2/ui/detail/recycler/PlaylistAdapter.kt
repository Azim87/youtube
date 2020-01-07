package com.example.kotlin2.ui.detail.recycler

import ItemsItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.kotlin2.R


class PlaylistAdapter(private val function: (ItemsItem) -> Unit) :
    RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {
    private var mList = mutableListOf<ItemsItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        return PlaylistViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_youtube_playlist, parent, false), function)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.onBind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun submitList(list: List<ItemsItem>) {
        mList = list as MutableList<ItemsItem>
        notifyDataSetChanged()
    }

    class PlaylistViewHolder constructor(itemView: View, val function: (ItemsItem) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.detail_textView)
        private val imageView: ImageView = itemView.findViewById(R.id.detail_image_view)

        fun onBind(item: ItemsItem) {
            textView.text = item.snippet.title
            Glide
                .with(imageView.context)
                .load(item.snippet.thumbnails.high.url)
                .apply(RequestOptions()
                        .transform(RoundedCorners(20))
                        .error(R.drawable.ic_arrow_back).skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(imageView)

            itemView.setOnClickListener {
                function(item)
            }
        }
    }
}