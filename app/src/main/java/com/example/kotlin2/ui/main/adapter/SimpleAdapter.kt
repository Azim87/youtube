package com.example.kotlin2.ui.main.adapter

import com.example.kotlin2.model.ItemsItem
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
import com.example.kotlin2.util.load


class SimpleAdapter(private val function: (ItemsItem) -> Unit) :
    RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder>() {
    private var mList = mutableListOf<ItemsItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        return SimpleViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_main_viewholder, parent, false), function
        )
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        holder.onBind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun submitList(list: List<ItemsItem>?) {
        mList = list as MutableList<ItemsItem>
        notifyDataSetChanged()
    }

    class SimpleViewHolder constructor(itemView: View, val function: (ItemsItem) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.textView)
        private val textView2: TextView = itemView.findViewById(R.id.sub_title)
        private val imageView: ImageView = itemView.findViewById(R.id.image_view)

        fun onBind(item: ItemsItem) {
            textView.text = item.snippet.title
            textView2.text = item.contentDetails.itemCount + " video series"

            Glide.with(imageView.context)
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