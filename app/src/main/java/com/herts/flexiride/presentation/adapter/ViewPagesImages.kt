package com.herts.flexiride.presentation.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.herts.flexiride.R
import com.herts.flexiride.model.ImageList
import com.herts.flexiride.presentation.CarDetailsActivity

class ViewPagesImages(
    var context: Context,
    mImageList: List<ImageList>,
    var listner: CarDetailsActivity,
    posSelected: Int
) : PagerAdapter() {

    //    var imagesList = mImageList
    private var inflater: LayoutInflater? = null
    var imagesList: ArrayList<ImageList> = mImageList as ArrayList<ImageList>


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`

    }

    override fun getCount(): Int {
        return imagesList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater!!.inflate(R.layout.images_slider_item, null)


        val car_thumbnail = view.findViewById<ImageView>(R.id.imageView_slide)
        val imageByteArray: ByteArray = Base64.decode(imagesList.get(position)?.Path, Base64.DEFAULT)
        Glide.with(context).load(imageByteArray)
            .into(car_thumbnail)
        /*val decodedString: ByteArray = Base64.decode(imagesList.get(position)?.Path, Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        car_thumbnail.setImageBitmap(decodedByte)*/


        val img = view.findViewById<ImageView>(R.id.imageView_slide)
        img.setOnClickListener {
            // (context as CarDetailsActivity).imageViewShow(position)
        }
        val vp = container as ViewPager
        vp.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }

    interface onItemClick {

        fun Itemselected(position: Int)

    }
}