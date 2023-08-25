package com.herts.flexiride.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.herts.flexiride.R
import com.herts.flexiride.model.SignInRequest
import com.herts.flexiride.utils.Navigator
import com.herts.flexiride.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var vm: LoginViewModel
    var email_address: EditText? = null
    var etPassword: EditText? = null

    companion object {
        fun getCallingIntent(context: Context): Intent {
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        vm = ViewModelProvider(this)[LoginViewModel::class.java]

        val button: Button = findViewById(R.id.btn_login)
        button.setOnClickListener {
            if (checkValidations()) {
                callApi()
            }
        }

        val textSignup: TextView = findViewById(R.id.txt_view_dont_hv_acc)
        textSignup.setOnClickListener {
            Navigator.navigateToWelcomeActivity(this)
        }
    }

    private fun callApi() {

        val mSignInRequest = SignInRequest()
        mSignInRequest.password = etPassword?.text.toString()
        mSignInRequest.email = email_address?.text.toString()


//        vm.loginUser(mSignInRequest)
        vm.loginUser(email_address?.text.toString())

        vm.createPostLiveData?.observe(this, Observer {
            if (it != null) {

                if (it.password.toString() == etPassword?.text.toString()) {
                    val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putInt("USER_ID", it.userId?.toInt()!!)
                        apply()
                    }
                    if (it.roleId == 1)
                        Navigator.navigateToOwnerDashboardActivity(this)
//                        Navigator.navigateToAddAvailabilityActivity(this, 2, "London")
                    else
                        Navigator.navigateToHomeActivity(this)
                    showToast("Login successful!")
                } else {
                    showToast("Incorrect password!")
                }


            } else {
                showToast("Cannot create post at the moment")
            }

        })
    }

    private fun checkValidations(): Boolean {
        var flag: Boolean = true
        email_address = findViewById<EditText>(R.id.edit_text_email)
        val text_error = findViewById<TextView>(R.id.text_error_email)
        etPassword = findViewById<EditText>(R.id.etPassword_l)
        val text_error_password_login = findViewById<TextView>(R.id.text_error_password_login)

        email_address?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                s!!
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                text_error.visibility = View.GONE
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        etPassword?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                s!!
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                text_error_password_login.visibility = View.GONE
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        if (email_address?.text.isNullOrEmpty() || !vm.isEmailValid(email_address?.text.toString())) {
            email_address?.requestFocus()
            text_error.setText(R.string.enter_valid_email_add)
            text_error.visibility = View.VISIBLE
            flag = false
        }

        if (etPassword?.text.toString().isNullOrEmpty()) {
            text_error_password_login.setText(R.string.enter_password)
            text_error_password_login.visibility = View.VISIBLE
            flag = false
        } else if (!vm.isPasswordValid(etPassword?.text.toString())) {
            text_error_password_login.setText(R.string.password_8_20)
            text_error_password_login.visibility = View.VISIBLE
            flag = false
        }
        return flag
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}