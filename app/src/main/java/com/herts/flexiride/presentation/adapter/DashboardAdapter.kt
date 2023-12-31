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
import com.herts.flexiride.data.response.CarList
import okhttp3.internal.trimSubstring


class DashboardAdapter(var listener: HomeListener, var verify: Int) :
    RecyclerView.Adapter<DashboardAdapter.HomeViewHolder>() {

    private var data: ArrayList<CarList>? = null


    interface HomeListener {
        fun onItemDeleted(postModel: CarList, position: Int)
    }

    fun setData(list: ArrayList<CarList>) {
        data = list
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.my_car, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = data?.get(position)
        holder.bindView(item)
        holder.llVerify.setOnClickListener {
            item?.let { it1 ->
                listener.onItemDeleted(it1, position)
            }
        }
//        if (verify == 1) {
//            holder.llVerify.visibility = View.GONE
//        } else {
//            holder.llVerify.visibility = View.VISIBLE
//        }

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
        val txt_brand_name = itemView.findViewById<TextView>(R.id.txt_brand_name)
        val txt_car_specification = itemView.findViewById<TextView>(R.id.txt_car_specification)
        val txt_ratings = itemView.findViewById<TextView>(R.id.txt_ratings)
        val total_trips = itemView.findViewById<TextView>(R.id.total_trips)
        val txt_fare = itemView.findViewById<TextView>(R.id.txt_fare)
        val frequency = itemView.findViewById<TextView>(R.id.frequency)
        val view_details = itemView.findViewById<LinearLayout>(R.id.view_details)
        val car_thumbnail = itemView.findViewById<ImageView>(R.id.car_thumbnail)
        val llVerify = itemView.findViewById<LinearLayout>(R.id.ll_earnings)
        val ll_booked = itemView.findViewById<LinearLayout>(R.id.ll_booked)
        val textView1 = itemView.findViewById<TextView>(R.id.textView1)


        fun bindView(item: CarList?) {


            val decodedString: ByteArray = Base64.decode(item?.image1, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            car_thumbnail.setImageBitmap(decodedByte)
            txt_brand_name.text = item?.brand + " " + item?.model
            txt_car_specification.text = item?.color + " (" + item?.manOfYear + ")"
            txt_ratings.text = "License plate no. " + item?.licencePlate
            total_trips.text = "Fare: £" + item?.fareAmount + "/hour"
            txt_fare.text =
                "Available from: " + item?.fromDate?.trimSubstring(
                    0,
                    10
                ) + " - " + item?.toDate?.trimSubstring(
                    0,
                    10
                )

            if (item?.verified?.isNotEmpty()!!) {
                ll_booked.visibility = View.VISIBLE
                if (item?.verified == "No") {
                    textView1.setText("No Damages")
                } else {
                    textView1.setText("Damage Detected")
                }
            } else {
                ll_booked.visibility = View.GONE
            }

            if (item?.brandVerified?.isNotEmpty()!!) {
                if (item?.brandVerified == "Yes") {
                    llVerify.visibility = View.GONE
                    txt_brand_name.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.baseline_check_24,
                        0
                    )
                } else {
                    txt_brand_name.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.baseline_close_24,
                        0
                    )
                }
            } else {
                txt_brand_name.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            }

            if (item?.yearVerified?.isNotEmpty()!!) {
                if (item?.yearVerified == "Yes") {
                    txt_car_specification.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.baseline_check_24,
                        0
                    )
                } else {
                    txt_car_specification.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.baseline_close_24,
                        0
                    )
                }
            } else {
                txt_car_specification.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            }

        }

    }

}