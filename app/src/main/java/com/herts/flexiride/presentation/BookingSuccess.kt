package com.herts.flexiride.presentation

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.herts.flexiride.R
import com.herts.flexiride.data.response.CarList
import com.herts.flexiride.utils.Navigator

class BookingSuccess : AppCompatActivity() {

    var bookingId: Int = 0

    companion object {
        var BOOKING_ID: String = "BOOKING_ID"
        fun getCallingIntent(context: Context, id: Int): Intent {
            val intent = Intent(context, BookingSuccess::class.java)
            intent.putExtra(BOOKING_ID, id)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_success)
        val bundle = intent.extras
        if (bundle != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bookingId = intent.getIntExtra(BOOKING_ID, 0)
            } else {
                bookingId = intent.getIntExtra(BOOKING_ID, 0)
            }
        }
        val booking = findViewById<TextView>(R.id.booking_id)
        booking.setText(bookingId.toString())

        val btn_end = findViewById<Button>(R.id.btn_end)
        btn_end.setOnClickListener {
            Navigator.navigateToHomeActivity(this)
        }
    }
}