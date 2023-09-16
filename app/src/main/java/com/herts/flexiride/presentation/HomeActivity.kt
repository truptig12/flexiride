package com.herts.flexiride.presentation

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.herts.flexiride.R
import com.herts.flexiride.data.response.CarList
import com.herts.flexiride.presentation.adapter.HomeAdapter
import com.herts.flexiride.utils.Navigator
import com.herts.flexiride.viewmodel.HomeViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class HomeActivity : AppCompatActivity(), HomeAdapter.HomeListener {

    private lateinit var vm: HomeViewModel
    private lateinit var adapter: HomeAdapter
    var rv_home: RecyclerView? = null

    var txt_view_date: TextView? = null
    var txt_end_date: TextView? = null
    var spinner_city: Spinner? = null
    var cal = Calendar.getInstance()
    var leftDays: String = "1"

    companion object {
        fun getCallingIntent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        vm = ViewModelProvider(this)[HomeViewModel::class.java]
        initAdapter()
        val img_view_find_cars = findViewById<ImageView>(R.id.img_view_find_cars)
        img_view_find_cars.setOnClickListener {
            if (isCredentialsValid()) {
                val millionSeconds = toDate?.time?.minus(fromDate?.time!!)
                leftDays = TimeUnit.MILLISECONDS.toDays(millionSeconds!!).toString()
                Log.d("left Days: ", leftDays)

                callApi()
            } else {
                showToast("Enter correct details!")
            }
        }
        txt_view_date = findViewById(R.id.txt_view_date)
        txt_end_date = findViewById(R.id.txt_end_date)
        spinner_city = findViewById(R.id.spinner_city)
        initDateListener()
    }

    private fun callApi() {
        val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE) ?: return
        val token = sharedPref.getString("TOKEN", "")
        vm.fetchAllCars(
            token.toString(),
            spinner_city?.selectedItem.toString(),
            txt_view_date?.text.toString(),
            txt_end_date?.text.toString()
        )
        vm.postModelListLiveData?.observe(this, Observer {
            if (it != null) {
                rv_home?.visibility = View.VISIBLE
                if (it.carList != null)
                    adapter.setData(it.carList as ArrayList<CarList>)
                else
                    showToast("No Cars found")
            } else {
                showToast("Something went wrong")
            }
        })
    }

    private fun isCredentialsValid(): Boolean {
        var flag: Boolean = true

        if (spinner_city?.selectedItem == "Select City") {
            showToast("Please enter location")
            flag = false
        }

        if (txt_view_date?.text.isNullOrEmpty()) {
            showToast("Please enter Start date")
            flag = false
        }
        if (txt_end_date?.text.isNullOrEmpty()) {
            showToast("Please enter End date")
            flag = false
        }
        return flag
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

        val cons_lyt_check_in = findViewById<RelativeLayout>(R.id.cons_lyt_check_in)
        val rl_checkout = findViewById<RelativeLayout>(R.id.rl_checkout)
        cons_lyt_check_in!!.setOnClickListener {
            DatePickerDialog(
                this@HomeActivity,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        rl_checkout!!.setOnClickListener {
            DatePickerDialog(
                this@HomeActivity,
                dateSetListener1,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    var fromDate: Date? = null
    var toDate: Date? = null

    private fun updateDateInView() {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        txt_view_date!!.text = sdf.format(cal.getTime())
        fromDate = cal.time
    }

    private fun updateDateInView1() {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        txt_end_date!!.text = sdf.format(cal.getTime())
        toDate = cal.time
    }

    private fun initAdapter() {
        adapter = HomeAdapter(this)
        rv_home = findViewById<RecyclerView>(R.id.rv_home)
        rv_home?.layoutManager = LinearLayoutManager(this)
        rv_home?.adapter = adapter
    }

    override fun onItemView(postModel: CarList, position: Int) {
        Navigator.navigateToCarDetailsActivity(this, postModel, leftDays)

    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}