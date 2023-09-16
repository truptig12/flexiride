package com.herts.flexiride.presentation

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.herts.flexiride.R
import com.herts.flexiride.data.request.AddBookingRequest
import com.herts.flexiride.data.response.CarList
import com.herts.flexiride.model.ImageList
import com.herts.flexiride.presentation.adapter.ImagesAdapter
import com.herts.flexiride.presentation.adapter.ViewPagesImages
import com.herts.flexiride.utils.Navigator
import com.herts.flexiride.viewmodel.BookingViewModel

class CarDetailsActivity : AppCompatActivity(), ImagesAdapter.onItemClick {
    private lateinit var vm: BookingViewModel
    var dta: CarList? = null
    lateinit var imagesAdapter: ImagesAdapter
    var mImageList = ArrayList<ImageList>()
    var recyl_car: RecyclerView? = null
    var viewpager: ViewPager? = null
    var image: ImageView? = null
    var bookingAmount: Float = 0F
    var noOfDays: Int = 1

    companion object {
        var CAR_DETAILS: String = "CAR_DETAILS"
        var NO_OF_DAYS: String = "NO_OF_DAYS"
        fun getCallingIntent(context: Context, car: CarList, leftDays: String): Intent {
            val intent = Intent(context, CarDetailsActivity::class.java)
            intent.putExtra(CAR_DETAILS, car)
            intent.putExtra(NO_OF_DAYS, leftDays)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_details)
        vm = ViewModelProvider(this)[BookingViewModel::class.java]
        val bundle = intent.extras
        if (bundle != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                dta = intent.getSerializableExtra(CAR_DETAILS, CarList::class.java)
                noOfDays = intent.getStringExtra(NO_OF_DAYS)!!?.toInt()!!
            } else {
                dta = intent.getSerializableExtra(CAR_DETAILS) as CarList
                noOfDays = intent.getStringExtra(NO_OF_DAYS)!!?.toInt()!!
            }
        }
        image = findViewById<ImageView>(R.id.image)
        image?.clipToOutline = true
        val decodedString: ByteArray = Base64.decode(dta?.image1, Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        image?.setImageBitmap(decodedByte)
        createImageList()
        setData()
        val book_now = findViewById<LinearLayout>(R.id.book_now)
        book_now.setOnClickListener {
            callApi()
        }
    }

    private fun callApi() {
        val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE) ?: return
        val userID = sharedPref.getInt("USER_ID", 1)
        val token = sharedPref.getString("TOKEN", "")
        val addBookingRequest = AddBookingRequest()
        addBookingRequest.userId = dta?.userId!!
        addBookingRequest.bookedUserId = userID
        addBookingRequest.carId = dta?.carId!!
        addBookingRequest.fromDate = dta?.fromDate!!
        addBookingRequest.toDate = dta?.toDate!!
        addBookingRequest.bookingAmount = bookingAmount.toInt()
        vm.addBooking(token.toString(), addBookingRequest)

        vm.createPostLiveData?.observe(this, Observer {
            if (it.id != null) {
                Navigator.navigateToBookingSuccess(this, it.id!!)
            } else {
                showToast("Cannot add car at the moment")
            }

        })

    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun createImageList() {
        mImageList.clear()
        val imageList = ImageList()
        imageList.Path = dta?.image1
        imageList.FileName = "image1"

        val imageList2 = ImageList()
        imageList2.Path = dta?.image2
        imageList2.FileName = "image2"

        val imageList3 = ImageList()
        imageList3.Path = dta?.image3
        imageList3.FileName = "image3"

        val imageList4 = ImageList()
        imageList4.Path = dta?.image4
        imageList4.FileName = "image4"


        mImageList.add(imageList)
        mImageList.add(imageList2)
        mImageList.add(imageList3)
        mImageList.add(imageList4)

        recyl_car = findViewById<RecyclerView>(R.id.recyl_car)
    }

    private fun setData() {
        val txt_brand_name = findViewById<TextView>(R.id.txt_brand_name)
        val txt_car_specification = findViewById<TextView>(R.id.txt_car_specification)
        txt_brand_name.setText(dta?.brand + " " + dta?.model + "\n" + dta?.manOfYear)
        txt_car_specification.setText(dta?.color + "\n" + "License plate: " + dta?.licencePlate)
        val seat = findViewById<TextView>(R.id.seat)
        seat.setText(dta?.noOfSeats)
        val fuel = findViewById<TextView>(R.id.fuel)
        fuel.setText(dta?.fuelType)
        val gear = findViewById<TextView>(R.id.gear)
        gear.setText(dta?.gearbox)
        val engine = findViewById<TextView>(R.id.engine)
        engine.setText(dta?.engine)
        val txt_pk = findViewById<TextView>(R.id.txt_pk)
        txt_pk.setText("£" + dta?.fareAmount + " / hour")

        val pd = findViewById<TextView>(R.id.pd)
        val amt = dta?.fareAmount?.toFloat()?.times(24)
        pd.setText("£ " + amt.toString())

        //calculation for days
        val xx = findViewById<TextView>(R.id.xx)
        val tt = amt?.times(noOfDays)
        xx.setText("£ " + tt.toString())
        bookingAmount = tt!!

        imageSliderImplementation()
        setImageAdapter()
    }

    private fun imageSliderImplementation() {

        image?.visibility = View.GONE
        val adapter = ViewPagesImages(this, mImageList, this, 0)
        viewpager = findViewById<ViewPager>(R.id.viewpager)
        viewpager?.adapter = adapter

        viewpager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                imagesAdapter.setData(position)
                recyl_car?.scrollToPosition(position)
            }

        })
        viewpager?.background = null
    }


    private fun setImageAdapter() {


        val linearLayoutManagerImages =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyl_car?.layoutManager = linearLayoutManagerImages
        imagesAdapter =
            this.let {
                ImagesAdapter(
                    this, mImageList,
                    this
                )
            }
        recyl_car?.adapter = imagesAdapter
    }

    override fun onClicked(position: Int) {
        Log.d("pos", position.toString())
        viewpager?.setCurrentItem(position)
    }
}