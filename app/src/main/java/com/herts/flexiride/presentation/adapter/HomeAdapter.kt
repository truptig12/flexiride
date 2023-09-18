package com.herts.flexiride.presentation.adapter

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.herts.flexiride.R
import com.herts.flexiride.data.response.CarList


class HomeAdapter(var listener: HomeListener) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var data: ArrayList<CarList>? = null

    interface HomeListener {
        fun onItemView(
            postModel: Int?,
            fromDate: String?,
            toDate: String?,
            fareAmount: String?,
            position: Int
        )
    }

    fun setData(list: ArrayList<CarList>) {
        data = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.tu_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = data?.get(position)
        holder.bindView(item)
        holder.car_layout.setOnClickListener {
            item?.let { it1 ->
                listener.onItemView(it1.carId,it1.fromDate, it1.toDate, it1.fareAmount,position)
            }
        }
    }

    fun addData(postModel: CarList) {
        data?.add(0, postModel)
        notifyItemInserted(0)
    }

    fun removeData(position: Int) {
        data?.removeAt(position)
        notifyDataSetChanged()
    }

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_tuv_name = itemView.findViewById<TextView>(R.id.txt_tuv_name)
        val txt_car_specification = itemView.findViewById<TextView>(R.id.txt_car_specification)
        val txt_fare = itemView.findViewById<TextView>(R.id.txt_fare)
        val reviews = itemView.findViewById<TextView>(R.id.reviews)
        val car_layout = itemView.findViewById<CardView>(R.id.car_layout)
        val car_thumbnail = itemView.findViewById<ImageView>(R.id.car_thumbnail)
        fun bindView(item: CarList?) {

            val decodedString: ByteArray = Base64.decode(item?.image1, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            car_thumbnail.setImageBitmap(decodedByte)
            txt_tuv_name.text = item?.brand + " " + item?.model
            txt_car_specification.text =
                item?.licencePlate + " " + item?.color + " (" + item?.manOfYear + ")"
            txt_fare.text = item?.noOfSeats + " seater, " + item?.fuelType
            reviews.text = "£" + item?.fareAmount + " / hour"
        }

    }

}