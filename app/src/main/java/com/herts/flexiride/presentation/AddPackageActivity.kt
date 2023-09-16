package com.herts.flexiride.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.herts.flexiride.R
import com.herts.flexiride.data.request.AddPackageRequest
import com.herts.flexiride.utils.Navigator
import com.herts.flexiride.viewmodel.CarViewModel

class AddPackageActivity : AppCompatActivity() {

    private lateinit var vm: CarViewModel

    var et_fare: EditText? = null
    var edt_duration: EditText? = null

    companion object {
        fun getCallingIntent(context: Context, id: Int): Intent {
            val intent = Intent(context, AddPackageActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_package)

        vm = ViewModelProvider(this)[CarViewModel::class.java]
        val button_package = findViewById<Button>(R.id.btn_next_package)
        button_package.setOnClickListener {
            if (isCredentialsValid()) {
                callApi()
            } else {
                showToast("Enter correct details!")
            }
        }
    }

    private fun callApi() {
        val addPackageRequest = AddPackageRequest()
        addPackageRequest.userId = 2
        addPackageRequest.carId = 4
        addPackageRequest.duration = edt_duration?.text.toString()
        addPackageRequest.fareAmount = et_fare?.text.toString().toLong()

       // vm.addPackage(addPackageRequest)

        vm.createPackageLiveData?.observe(this, Observer {
            if (it.id != null) {

            } else {
                showToast("Cannot add car at the moment")
            }

        })

    }

    private fun isCredentialsValid(): Boolean {
        var flag: Boolean = true
        et_fare = findViewById(R.id.et_fare)
        edt_duration = findViewById(R.id.edt_duration)
        val error_fare_amt = findViewById<TextView>(R.id.error_fare_amt)
        val error_duration = findViewById<TextView>(R.id.error_duration)
        if (et_fare?.text.isNullOrEmpty()) {
            error_fare_amt.setText(R.string.fare_amt_error)
            error_fare_amt.visibility = View.VISIBLE
            flag = false
        }

        if (edt_duration?.text.isNullOrEmpty()) {
            error_duration.setText(R.string.fare_amt_error)
            error_duration.visibility = View.VISIBLE
            flag = false
        }
        return flag
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}