package com.example.kotlin2.ui.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlin2.R

class SimpleAdapter : RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder>() {
    private var mList: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        return SimpleViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_main_viewholder, parent, false))
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        holder.onBind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun submitList(list: List<String>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    class SimpleViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.textView)
        private val imageView: ImageView = itemView.findViewById(R.id.image_view)

        fun onBind(text: String) {
            Glide.with(imageView.context).load(text).into(imageView)
        }
    }
}