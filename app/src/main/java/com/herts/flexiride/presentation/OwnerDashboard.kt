package com.herts.flexiride.presentation

import android.R.attr.textColor
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.herts.flexiride.R
import com.herts.flexiride.data.response.BookingList
import com.herts.flexiride.data.response.CarList
import com.herts.flexiride.network.ApiClient
import com.herts.flexiride.network.ApiInterface
import com.herts.flexiride.presentation.adapter.BookingAdapter
import com.herts.flexiride.presentation.adapter.DashboardAdapter
import com.herts.flexiride.utils.Navigator
import com.herts.flexiride.viewmodel.DashboardViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class OwnerDashboard : AppCompatActivity(), DashboardAdapter.HomeListener,
    BookingAdapter.HomeListener {

    private lateinit var vm: DashboardViewModel
    private lateinit var adapter: DashboardAdapter
    private lateinit var bookingAdapter: BookingAdapter
    var rv_dashboard: RecyclerView? = null
    var rv_bookings: RecyclerView? = null
    var userID: Int = 0
    var txt_up: TextView? = null
    var main: ConstraintLayout? = null
    var token: String = ""
//    var dta: SignInResponse? = null

    companion object {
        var USER_DETAILS: String = "USER_DETAILS"

        fun getCallingIntent(context: Context): Intent {
            val intent = Intent(context, OwnerDashboard::class.java)
//            intent.putExtra(USER_DETAILS, signInResponse)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_dashboard)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true);

        vm = ViewModelProvider(this)[DashboardViewModel::class.java]

        /* val bundle = intent.extras
         if (bundle != null) {
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                 dta = intent.getSerializableExtra(USER_DETAILS, SignInResponse::class.java)
             } else {
                 dta = intent.getSerializableExtra(USER_DETAILS) as SignInResponse
             }
         }*/
        main = findViewById(R.id.main)

        initAdapter()
        txt_up = findViewById(R.id.txt_up)
        val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE) ?: return
        userID = sharedPref.getInt("USER_ID", 1)
        token = sharedPref.getString("TOKEN", "").toString()
        val noData = findViewById<LinearLayout>(R.id.noData)
        vm.fetchAllCars(token.toString(), userID)
        vm.postModelListLiveData?.observe(this, Observer {
            if (it != null) {
                if (it.carList != null) {
                    noData.visibility = View.GONE
                    rv_dashboard?.visibility = View.VISIBLE
                    txt_up?.visibility = View.VISIBLE
                    adapter.setData(it.carList as ArrayList<CarList>)
                    callBookingsApi()
                } else {
                    noData.visibility = View.VISIBLE
                    rv_dashboard?.visibility = View.GONE
                    txt_up?.visibility = View.GONE
                }

            } else {
                noData.visibility = View.VISIBLE
                rv_dashboard?.visibility = View.GONE
//                showToast("Something went wrong")
            }

        })
        val frm_lyt_add_tuv = findViewById<FrameLayout>(R.id.frm_lyt_add_tuv)
        frm_lyt_add_tuv.setOnClickListener {
            Navigator.navigateToAddNewCarActivity(this)
        }

    }

    private fun callBookingsApi() {

        vm.fetchAllBookings(token, userID)
        vm.postBookingListLiveData?.observe(this, Observer {
            if (it != null) {
                rv_bookings?.visibility = View.VISIBLE
                if (it.bookingList != null)
                    bookingAdapter.setData(it.bookingList as ArrayList<BookingList>)
                else
                    rv_bookings?.visibility = View.GONE
            } else {
                rv_bookings?.visibility = View.GONE
            }

        })
    }

    private fun initAdapter() {
        val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE) ?: return
        val verify = sharedPref.getInt("VERIFIED", 0)

        adapter = DashboardAdapter(this, verify)
        rv_dashboard = findViewById<RecyclerView>(R.id.rv_dashboard)
        rv_dashboard?.layoutManager = LinearLayoutManager(this)
        rv_dashboard?.adapter = adapter

        bookingAdapter = BookingAdapter(this)
        rv_bookings = findViewById<RecyclerView>(R.id.rv_bookings)
        rv_bookings?.layoutManager = LinearLayoutManager(this)
        rv_bookings?.adapter = bookingAdapter


    }


    override fun onItemDeleted(postModel: CarList, position: Int) {

        val file = saveImage(this, postModel.image1)
        val service = ApiClient.getPythonApiClient()!!.create(ApiInterface::class.java)
        val reqFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file!!)
        val body = MultipartBody.Part.createFormData("image", file.name, reqFile)
        val name = RequestBody.create("text/plain".toMediaTypeOrNull(), "CarImageCheck")

        val req: Call<ResponseBody> = service.postImage(body, name)
        req.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>?,
                response: Response<ResponseBody?>?
            ) {
                // Do Something

                if (response?.body() != null) {
                    val jsonData: String = response.body()!!.string()
                    val json = JSONObject(jsonData.trim())
                    val license = json.get("number_plate")
                    val car_status = json.get("car_status")
                    val car_model = json.get("car_model")
                    if (license.toString() == postModel.licencePlate) {


                    }
                    if (car_status.toString() == "01-whole") {
                        postModel.verified = "No"
                    } else {
                        postModel.verified = "Yes"
                    }
                    if (car_model.toString().substringBefore(" ") == postModel.brand) {

                        postModel.brandVerified = "Yes"

                    } else {
                        postModel.brandVerified = "No"
                    }
                    if (car_model.toString().substringAfterLast(" ") == postModel.manOfYear) {
                        postModel.yearVerified = "Yes"

                    } else {
                        postModel.yearVerified = "No"
                    }
                    Log.d("params: ", "$license $car_status $car_model")
                    adapter.notifyItemChanged(position)

                    val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putInt("VERIFIED", 1)
                        apply()
                    }


                }


//                Toast.makeText(this@OwnerDashboard, response.toString(), Toast.LENGTH_LONG).show()

                val snack = main?.rootView?.let {
                    Snackbar.make(
                        it,
                        response?.body()!!.string(),
                        8000
                    )
                }
                snack?.view?.setBackgroundColor(
                    ContextCompat.getColor(
                        this@OwnerDashboard,
                        R.color.background_grey
                    )
                );
                snack?.view?.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                    ?.setTextColor(ContextCompat.getColor(this@OwnerDashboard, R.color.white))
                snack?.show()

            }

            override fun onFailure(call: Call<ResponseBody?>?, t: Throwable) {
                t.printStackTrace()
            }
        })

    }

    fun saveImage(context: Context, imageData: String?): File? {
        val imgBytesData = Base64.decode(
            imageData,
            Base64.DEFAULT
        )
        val file = File.createTempFile("image", null, context.cacheDir)
        val fileOutputStream: FileOutputStream
        fileOutputStream = try {
            FileOutputStream(file)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            return null
        }
        val bufferedOutputStream = BufferedOutputStream(
            fileOutputStream
        )
        try {
            bufferedOutputStream.write(imgBytesData)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            try {
                bufferedOutputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return file
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onItemRejected(postModel: BookingList, position: Int) {
        vm.rejectBooking(token, postModel.bookingId!!)
        vm.rejectBookingLiveData?.observe(this, Observer {
            if (it != null) {
                showToast("Booking rejected successfully!")
                postModel.booked = 2
                bookingAdapter.notifyItemChanged(position)
            } else {
                showToast("Failed to reject booking")
            }

        })

    }

    private fun callAcceptApi(postModel: BookingList, position: Int) {
        vm.acceptBooking(token, postModel.bookingId!!)
        vm.acceptBookingLiveData?.observe(this, Observer {
            if (it != null) {
                showToast("Booking accepted successfully!")
                postModel.booked = 1
                bookingAdapter.notifyItemChanged(position)
            } else {
                showToast("Failed to accept booking")
            }

        })
    }

    override fun onItemAccepted(postModel: BookingList, position: Int) {
        callAcceptApi(postModel, position)
    }

}