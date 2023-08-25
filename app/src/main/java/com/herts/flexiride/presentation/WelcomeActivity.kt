package com.herts.flexiride.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.herts.flexiride.R
import com.herts.flexiride.utils.Navigator

class WelcomeActivity : AppCompatActivity() {

    companion object {
        fun getCallingIntent(context: Context): Intent {
            val intent = Intent(context, WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val btn_owner = findViewById<Button>(R.id.btn_owner)
        val btn_renter = findViewById<Button>(R.id.btn_renter)
        val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE)

        btn_owner.setOnClickListener {
            with (sharedPref.edit()) {
                putInt("ROLE", 1)
                apply()
            }
            Navigator.navigateToRegisterActivity(this)
        }

        btn_renter.setOnClickListener {
            with (sharedPref.edit()) {
                putInt("ROLE", 2)
                apply()
            }
            Navigator.navigateToRegisterActivity(this)
        }

    }
}