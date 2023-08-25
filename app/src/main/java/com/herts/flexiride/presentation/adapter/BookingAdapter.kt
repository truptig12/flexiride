package com.herts.flexiride.presentation.adapter

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.herts.flexiride.R
import com.herts.flexiride.data.response.BookingList
import okhttp3.internal.trimSubstring


class BookingAdapter(var listener: HomeListener) :
    RecyclerView.Adapter<BookingAdapter.HomeViewHolder>() {

    private var data: ArrayList<BookingList>? = null

    interface HomeListener {
        fun onItemRejected(postModel: BookingList, position: Int)
        fun onItemAccepted(postModel: BookingList, position: Int)
    }

    fun setData(list: ArrayList<BookingList>?) {
        data = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.book_car, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = data?.get(position)
        holder.bindView(item)
        if (item?.booked == 0) {
            holder.ll_main.visibility = View.VISIBLE
        } else {
            holder.ll_main.visibility = View.GONE
        }

        holder.aacept.setOnClickListener {
            item?.let { it1 ->
                listener.onItemAccepted(it1, position)
            }
        }

        holder.reject.setOnClickListener {
            item?.let { it1 ->
                listener.onItemRejected(it1, position)
            }
        }
    }

    fun addData(postModel: BookingList) {
        data?.add(0, postModel)
        notifyItemInserted(0)
    }

    fun removeData(position: Int) {
        data?.removeAt(position)
        notifyDataSetChanged()
    }

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_brand_name = itemView.findViewById<TextView>(R.id.txt_brand_name)
        val txt_car_specification = itemView.findViewById<TextView>(R.id.txt_car_specification)
        val txt_ratings = itemView.findViewById<TextView>(R.id.txt_ratings)
        val total_trips = itemView.findViewById<TextView>(R.id.total_trips)
        val txt_fare = itemView.findViewById<TextView>(R.id.txt_fare)
        val frequency = itemView.findViewById<TextView>(R.id.frequency)
        val car_thumbnail = itemView.findViewById<ImageView>(R.id.car_thumbnail)
        val aacept = itemView.findViewById<LinearLayout>(R.id.ll_accept)
        val reject = itemView.findViewById<LinearLayout>(R.id.ll_reject)
        val ll_main = itemView.findViewById<LinearLayout>(R.id.ll_main)
        fun bindView(item: BookingList?) {

            val decodedString: ByteArray = Base64.decode(item?.car?.image1, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            car_thumbnail.setImageBitmap(decodedByte)
            txt_brand_name.text = item?.car?.brand + " " + item?.car?.model
            txt_car_specification.text = item?.car?.color + " (" + item?.car?.manOfYear + ")"
            txt_ratings.text = "License plate no. " + item?.car?.licencePlate
            total_trips.text = "Booking amount: £" + item?.bookingAmount
            txt_fare.text =
                "Booked from: " + item?.fromDate?.trimSubstring(
                    0,
                    10
                ) + " - " + item?.toDate?.trimSubstring(
                    0,
                    10
                )
            frequency.setText("Booked By: " + item?.user?.first_name + " " + item?.user?.last_name)


        }

    }

}