package com.herts.flexiride.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.herts.flexiride.data.repository.RegisterRepository
import com.herts.flexiride.data.request.SignupRequest
import com.herts.flexiride.data.response.SignupResponse
import java.util.regex.Pattern

class RegisterViewModel(application: Application): AndroidViewModel(application){

    private var homeRepository:RegisterRepository?=null
    var createPostLiveData:LiveData<SignupResponse>?=null


    init {
        homeRepository = RegisterRepository()
        createPostLiveData = MutableLiveData()

    }

    fun createUser(signupRequest: SignupRequest){
        createPostLiveData = homeRepository?.createUser(signupRequest)
    }


    fun isEmailValid(email: String): Boolean {
        val expression =
            "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    fun isPasswordValid(password: String): Boolean {
        val expression = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+\$).{8,20}"
        val pattern = Pattern.compile(expression)
        val matcher = pattern.matcher(password)
        return matcher.matches()
    }
}