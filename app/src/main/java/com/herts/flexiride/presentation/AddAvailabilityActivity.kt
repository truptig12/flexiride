package com.herts.flexiride.presentation

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.herts.flexiride.R
import com.herts.flexiride.data.request.AddAvailabilityRequest
import com.herts.flexiride.utils.Navigator
import com.herts.flexiride.viewmodel.CarViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddAvailabilityActivity : AppCompatActivity() {

    private lateinit var vm: CarViewModel
    var tv_start_d: TextView? = null
    var tv_end_d: TextView? = null
    var et_fare: EditText? = null
    var cal = Calendar.getInstance()
    var carId: Int = 0
    var city: String = ""

    companion object {
        var CAR_ID: String = "CAR_ID"
        var CITY: String = "CITY"
        fun getCallingIntent(context: Context, id: Int, city: String): Intent {
            val intent = Intent(context, AddAvailabilityActivity::class.java)
            intent.putExtra(CAR_ID, id)
            intent.putExtra(CITY, city)
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_availability)

        vm = ViewModelProvider(this)[CarViewModel::class.java]

        val bundle = intent.extras
        if (bundle != null) {
            carId = bundle.getInt(CAR_ID, 0)
            city = bundle.getString(CITY, "")
        }
        tv_start_d = findViewById<TextView>(R.id.tv_start_d)
        tv_end_d = findViewById<TextView>(R.id.tv_end_d)
        initDateListener()

        val btn_available = findViewById<Button>(R.id.btn_next_available)
        btn_available.setOnClickListener {
            if (isCredentialsValid())
                callApi()

//            Navigator.navigateToOwnerDashboardActivity(this)
            else
                showToast("Enter correct details!")
        }

    }

    private fun initDateListener() {
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }

        val dateSetListener1 =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView1()
            }

        val ll_start = findViewById<LinearLayout>(R.id.ll_start)
        val ll_end_date = findViewById<LinearLayout>(R.id.ll_end_date)
        ll_start!!.setOnClickListener {
            DatePickerDialog(
                this@AddAvailabilityActivity,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        ll_end_date!!.setOnClickListener {
            DatePickerDialog(
                this@AddAvailabilityActivity,
                dateSetListener1,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }


    private fun isCredentialsValid(): Boolean {
        var flag: Boolean = true
        val text_error_start = findViewById<TextView>(R.id.text_error_start)
        val text_error_end_d = findViewById<TextView>(R.id.text_error_end_d)
        val error_fare_amt = findViewById<TextView>(R.id.error_fare_amt)

        et_fare = findViewById(R.id.et_fare)

        if (tv_start_d?.text.isNullOrEmpty()) {
            text_error_start.setText(R.string.fare_amt_error)
            text_error_start.visibility = View.VISIBLE
            flag = false
        }
        if (tv_end_d?.text.isNullOrEmpty()) {
            text_error_end_d.setText(R.string.fare_amt_error)
            text_error_end_d.visibility = View.VISIBLE
            flag = false
        }

        if (et_fare?.text.isNullOrEmpty()) {
            error_fare_amt.setText(R.string.fare_amt_error)
            error_fare_amt.visibility = View.VISIBLE
            flag = false
        }


        return flag
    }

    private fun updateDateInView() {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        tv_start_d!!.text = sdf.format(cal.getTime())
    }

    private fun updateDateInView1() {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        tv_end_d!!.text = sdf.format(cal.getTime())
    }

    private fun callApi() {
        val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE) ?: return
        val userID = sharedPref.getInt("USER_ID", 1)

        val addAvailabilityRequest = AddAvailabilityRequest()
        addAvailabilityRequest.userId = userID
        addAvailabilityRequest.carId = carId
        addAvailabilityRequest.fromDate = tv_start_d?.text.toString()
        addAvailabilityRequest.toDate = tv_end_d?.text.toString()
        addAvailabilityRequest.fareAmount = et_fare?.text.toString()
        addAvailabilityRequest.city = city

        vm.addAvailability(addAvailabilityRequest)

        vm.createAvailabilityLiveData?.observe(this, Observer {
            if (it.id != null) {
                Navigator.navigateToOwnerDashboardActivity(this)
            } else {
                showToast("Cannot add availability at the moment")
            }

        })
    }


    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}