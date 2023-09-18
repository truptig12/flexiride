package com.herts.flexiride.presentation

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.herts.flexiride.R

class TermsAndPrivacy : AppCompatActivity() {


    companion object {
        var ID: String = "ID"
        fun getCallingIntent(context: Context, id: Int): Intent {
            val intent = Intent(context, TermsAndPrivacy::class.java)
            intent.putExtra(ID, id)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_and_privacy)

        val bundle = intent.extras
        var id = 0
        if (bundle != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                id = intent.getIntExtra(ID, 0)
            } else {
                id = intent.getIntExtra(ID, 0)
            }
            val text = findViewById<TextView>(R.id.terms)
            if (id == 1) {
                text.text = resources.getString(R.string.terms)
            } else {
                text.text = resources.getString(R.string.privacy)
            }
        }
    }
}