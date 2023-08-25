package com.herts.flexiride.presentation.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.herts.flexiride.R


class AddPhotosAdapter(
    var context: Context,
    val items: ArrayList<String>,
    val onCountClick: OnCountClickListener
) :
    RecyclerView.Adapter<AddPhotosAdapter.ViewHolder>() {
    var damageList = items
    var count: Int? = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_photos,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return damageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        if (requestType.equals("new")) {

        holder.thumbnail.setImageBitmap(BitmapFactory.decodeFile(damageList.get(position)))
//        } else {
//            Glide.with(context).load(Constant.IMAGE_BASE_URL + damageList.get(position))
//                .into(holder.thumbnail)
//        }

        holder.imgRemove.setOnClickListener {
            onCountClick.onCountClicked(position, damageList.get(position))
            damageList.remove(damageList.get(position))
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, items.size);

        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout = itemView.findViewById<CardView>(R.id.card_view_photos)
        val thumbnail = itemView.findViewById<ImageView>(R.id.thumbnail)
        val imgRemove = itemView.findViewById<ImageView>(R.id.remove)


    }

    interface OnCountClickListener {
        fun onCountClicked(count: Int?, item: String) {

        }
    }
}