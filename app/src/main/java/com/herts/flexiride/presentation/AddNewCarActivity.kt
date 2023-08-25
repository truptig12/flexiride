package com.herts.flexiride.presentation

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.herts.flexiride.R
import com.herts.flexiride.data.request.AddCarRequest
import com.herts.flexiride.utils.Navigator
import com.herts.flexiride.utils.YearPickerDialog
import com.herts.flexiride.viewmodel.CarViewModel

class AddNewCarActivity : AppCompatActivity() {
    private lateinit var vm: CarViewModel

    var tv_manu_year: TextView? = null
    var spinner_brand: Spinner? = null
    var model_name: EditText? = null
    var spinner_color: Spinner? = null
    var edittext_license: EditText? = null
    var edittext_engine: EditText? = null
    var spinner_add_state: Spinner? = null
    var spinner_seats: Spinner? = null
    var spinner_fuel: Spinner? = null
    var spinner_gear: Spinner? = null

    companion object {
        fun getCallingIntent(context: Context): Intent {
            val intent = Intent(context, AddNewCarActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_car)

        vm = ViewModelProvider(this)[CarViewModel::class.java]

        val btn_next = findViewById<Button>(R.id.btn_next)
        btn_next.setOnClickListener {
            if (isCredentialsValid()) {
                callApi()
            } else
                showToast("Enter correct details!")
//                Navigator.navigateToAddPhotosActivity(this)
        }
        tv_manu_year = findViewById<TextView>(R.id.tv_manuyear)
        val img_view_manu_year = findViewById<ImageView>(R.id.img_view_manuyear)
        var ondate =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                tv_manu_year?.setText(year.toString())
            }
        img_view_manu_year.setOnClickListener {
            val date = YearPickerDialog()
            date.setListener(ondate)
            date.show(supportFragmentManager, "Date Picker")
        }
    }

    private fun callApi() {
        val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE) ?: return
        val userID = sharedPref.getInt("USER_ID", 1)
        val addCarRequest = AddCarRequest()
        addCarRequest.userId = userID
        addCarRequest.brand = spinner_brand?.selectedItem.toString()
        addCarRequest.model = model_name?.text.toString()
        addCarRequest.color = spinner_color?.selectedItem.toString()
        addCarRequest.manOfYear = tv_manu_year?.text.toString()
        addCarRequest.licencePlate = edittext_license?.text.toString()
        addCarRequest.city = spinner_add_state?.selectedItem.toString()
        addCarRequest.noOfSeats = spinner_seats?.selectedItem.toString()
        addCarRequest.fuelType = spinner_fuel?.selectedItem.toString()
        addCarRequest.gearbox = spinner_gear?.selectedItem.toString()
        addCarRequest.engine = edittext_engine?.text.toString()

        vm.addCar(addCarRequest)

        vm.createPostLiveData?.observe(this, Observer {
            if (it.id != null) {
                Navigator.navigateToAddPhotosActivity(this, it.id!!,spinner_add_state?.selectedItem.toString() )
            } else {
                showToast("Cannot add car at the moment")
            }

        })


    }

    private fun isCredentialsValid(): Boolean {
        var flag: Boolean = true
        model_name = findViewById<EditText>(R.id.model_name)
        val text_error_model_name = findViewById<TextView>(R.id.text_error_model_name)
        val text_error_manu_year = findViewById<TextView>(R.id.text_error_manu_year)
        spinner_brand = findViewById<Spinner>(R.id.spinner_brand)
        val text_error_brand = findViewById<TextView>(R.id.text_error_brand)
        spinner_color = findViewById<Spinner>(R.id.spinner_color)
        val text_error_color = findViewById<TextView>(R.id.text_error_color)
        edittext_license = findViewById<EditText>(R.id.edittext_license)
        val text_error_license = findViewById<TextView>(R.id.text_error_license)
        edittext_engine = findViewById<EditText>(R.id.edittext_engine)
        val text_error_engine = findViewById<TextView>(R.id.text_error_engine)
        spinner_add_state = findViewById<Spinner>(R.id.spinner_add_state)
        val text_error_city = findViewById<TextView>(R.id.text_error_city)
        spinner_seats = findViewById<Spinner>(R.id.spinner_seats)
        val text_error_seats = findViewById<TextView>(R.id.text_error_seats)
        spinner_fuel = findViewById<Spinner>(R.id.spinner_fuel)
        val text_error_fuel = findViewById<TextView>(R.id.text_error_fuel)
        spinner_gear = findViewById<Spinner>(R.id.spinner_gear)
        val text_error_gear = findViewById<TextView>(R.id.text_error_gear)



        if (spinner_brand?.selectedItem.toString() == "Select Brand") {
            text_error_brand.setText(R.string.brand_name_error)
            text_error_brand.visibility = View.VISIBLE
            flag = false
        }

        if (model_name?.text.isNullOrEmpty()) {
            text_error_model_name.setText(R.string.model_name_error)
            text_error_model_name.visibility = View.VISIBLE
            flag = false
        }

        if (spinner_color?.selectedItem.toString() == "Select Color") {
            text_error_color.setText(R.string.color_name_error)
            text_error_color.visibility = View.VISIBLE
            flag = false
        }

        if (tv_manu_year?.text.isNullOrEmpty()) {
            text_error_manu_year.setText(R.string.manu_year_error)
            text_error_manu_year.visibility = View.VISIBLE
            flag = false
        }

        if (edittext_license?.text.isNullOrEmpty()) {
            text_error_license.setText(R.string.license_plate_error)
            text_error_license.visibility = View.VISIBLE
            flag = false
        }


        if (spinner_add_state?.selectedItem == null) {
            text_error_city.setText(R.string.city_error)
            text_error_city.visibility = View.VISIBLE
            flag = false
        }

        if (spinner_seats?.selectedItem.toString() == "Select Seats") {
            text_error_seats.setText(R.string.seat_error)
            text_error_seats.visibility = View.VISIBLE
            flag = false
        }

        if (spinner_fuel?.selectedItem.toString() == "Select Fuel") {
            text_error_fuel.setText(R.string.fuel_error)
            text_error_fuel.visibility = View.VISIBLE
            flag = false
        }

        if (spinner_gear?.selectedItem.toString() == "Select Gear") {
            text_error_gear.setText(R.string.gear_error)
            text_error_gear.visibility = View.VISIBLE
            flag = false
        }

        if (edittext_engine?.text.isNullOrEmpty()) {
            text_error_engine.setText(R.string.engine_cc_error)
            text_error_engine.visibility = View.VISIBLE
            flag = false
        }
        return flag

    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}