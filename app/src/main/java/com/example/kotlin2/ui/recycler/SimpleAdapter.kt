package com.example.kotlin2.ui.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlin2.R
import com.example.kotlin2.model.ItemsItem

class SimpleAdapter(val function: (ItemsItem) -> Unit) : RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder>() {
    private var mList = mutableListOf<ItemsItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        return SimpleViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_main_viewholder, parent, false), function)
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
        val textView: TextView = itemView.findViewById(R.id.textView)
        val imageView: ImageView = itemView.findViewById(R.id.image_view)



        public fun onBind(item: ItemsItem) {
            textView.text = item.snippet.title
            Glide.with(imageView.context).load(item.snippet.thumbnails.default.url).into(imageView)
            itemView.setOnClickListener {
                function(item)
            }
        }
    }
}