package com.herts.flexiride.presentation.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.herts.flexiride.R
import com.herts.flexiride.model.ImageList


class ImagesAdapter(
    var context: Context,
    mImageList: List<ImageList>,
    var listner: onItemClick
) : RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {
    var imagesList = mImageList
    var posSelected = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.images_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    fun setData(position: Int) {
        posSelected = position
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val imageByteArray: ByteArray =
            Base64.decode(imagesList.get(position)?.Path, Base64.DEFAULT)
        Glide.with(context).load(imageByteArray).centerCrop()
            .into(holder.thumbnail)

        if (posSelected == position) {
            holder.img_list.setImageResource(R.drawable.img_border)
        } else {
            holder.img_list.setImageResource(0)
        }
        holder.thumbnail.setOnClickListener {
            listner.onClicked(position)
            posSelected = position
            notifyDataSetChanged()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnail = itemView.findViewById<ImageView>(R.id.img_list_images)
        val img_list = itemView.findViewById<ImageView>(R.id.img_list)
//        val rl_img_layout = itemView.rl_img_layout
    }

    interface onItemClick {

        fun onClicked(position: Int)

    }
}