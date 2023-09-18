package com.herts.flexiride.presentation

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.herts.flexiride.R
import com.herts.flexiride.data.request.SignupRequest
import com.herts.flexiride.utils.Navigator
import com.herts.flexiride.viewmodel.RegisterViewModel


class RegisterActivity : AppCompatActivity() {

    private lateinit var vm: RegisterViewModel
    var email_address: EditText? = null
    var name: EditText? = null
    var last_name: EditText? = null
    var etPassword: EditText? = null


    companion object {
        fun getCallingIntent(context: Context): Intent {
            val intent = Intent(context, RegisterActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        vm = ViewModelProvider(this)[RegisterViewModel::class.java]

        makeTermsPolicyClickable()
        val button: Button = findViewById(R.id.btn_sign_up)
        val login: TextView = findViewById(R.id.text_view_already_have_an_account)
        button.setOnClickListener {
            if (checkValidations())
                callApi()
//            Navigator.navigateToHomeActivity(this)
            else
                showToast("Enter correct details!")
        }
        login.setOnClickListener {
            Navigator.navigateToLoginActivity(this)
        }


    }

    private fun makeTermsPolicyClickable() {
        val termsp = findViewById<TextView>(R.id.text_view_term_and_services)
        var text = termsp.text.toString()

        var spannableString = SpannableString(text)
        var clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                callTermsDialog(1)
            }
        }

        spannableString.setSpan(clickableSpan, 10, 27, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        spannableString.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    baseContext,
                    R.color.accent
                )
            ), 10, 27, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )


        var clickablePrivacy = object : ClickableSpan() {
            override fun onClick(widget: View) {
                callTermsDialog(2)
            }
        }

        spannableString.setSpan(clickablePrivacy, 32, 47, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)


        spannableString.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    baseContext,
                    R.color.accent
                )
            ), 32, 47, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        termsp.setText(spannableString)

        termsp.movementMethod = LinkMovementMethod.getInstance()

        termsp.highlightColor = Color.TRANSPARENT
    }

    private fun callTermsDialog(i: Int) {
        Navigator.navigateToPrivacy(this, i)

    }

    private fun callApi() {

        val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE) ?: return
        val role = sharedPref.getInt("ROLE", 2)
        val mSignupRequest = SignupRequest()
        mSignupRequest.firstName = name?.text.toString()
        mSignupRequest.lastName = last_name?.text.toString()
        mSignupRequest.password = etPassword?.text.toString()
        mSignupRequest.email = email_address?.text.toString()
        mSignupRequest.roleId = role

        vm.createUser(mSignupRequest)

        vm.createPostLiveData?.observe(this, Observer {
            if (it.id != null) {
                showToast("User registered successfully!")
                Navigator.navigateToLoginActivity(this)
            } else {
                showToast("Cannot create post at the moment")
            }

        })

    }


    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun checkValidations(): Boolean {

        email_address = findViewById<EditText>(R.id.email_address)
        val text_error = findViewById<TextView>(R.id.text_error)
        name = findViewById<EditText>(R.id.name)
        val text_error_fname = findViewById<TextView>(R.id.text_error_fname)
        last_name = findViewById<EditText>(R.id.last_name)
        val text_error_lname = findViewById<TextView>(R.id.text_error_lname)
        etPassword = findViewById<EditText>(R.id.etPassword)
        val text_error_password = findViewById<TextView>(R.id.text_error_password)
        val etPassword_re = findViewById<EditText>(R.id.etPassword_re)
        val text_error_password_re = findViewById<TextView>(R.id.text_error_password_re)

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

        name?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                s!!
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                text_error_fname.visibility = View.GONE
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        last_name?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                s!!
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                text_error_lname.visibility = View.GONE
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        etPassword?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                s!!
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                text_error_password.visibility = View.GONE
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        etPassword_re.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                s!!
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                text_error_password_re.visibility = View.GONE
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

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

        var flag: Boolean = true
        if (email_address?.text.isNullOrEmpty() || !vm.isEmailValid(email_address?.text.toString())) {
            email_address?.requestFocus()
            text_error.setText(R.string.enter_valid_email_add)
            text_error.visibility = View.VISIBLE
            flag = false
        }
        if (name?.text.isNullOrEmpty()) {
            name?.requestFocus()
            text_error_fname.setText(R.string.enter_first_name)
            text_error_fname.visibility = View.VISIBLE
            flag = false
        }
        if (last_name?.text.isNullOrEmpty()) {
            last_name?.requestFocus()
            text_error_lname.setText(R.string.last_name)
            text_error_lname.visibility = View.VISIBLE
            flag = false
        }
        if (etPassword?.text.isNullOrEmpty()) {
            etPassword?.requestFocus()
            text_error_password.setText(R.string.enter_password)
            text_error_password.visibility = View.VISIBLE
            flag = false
        }
        if (!vm.isPasswordValid(etPassword?.text.toString())) {
            text_error_password.setText(R.string.password_8_20)
            text_error_password.visibility = View.VISIBLE
            flag = false

        }
        if (etPassword_re.text.isNullOrEmpty()) {
            etPassword_re.requestFocus()
            text_error_password_re.setText(R.string.enter_retype_pass)
            text_error_password_re.visibility = View.VISIBLE
            flag = false
        }
        if (etPassword_re.text.toString() != etPassword?.text.toString()) {
            etPassword?.requestFocus()
            etPassword_re.requestFocus()
            flag = false
        }
        return flag

    }


}