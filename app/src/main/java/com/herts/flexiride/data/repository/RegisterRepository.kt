package com.herts.flexiride.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.herts.flexiride.data.request.SignupRequest
import com.herts.flexiride.data.response.SignupResponse
import com.herts.flexiride.network.ApiClient
import com.herts.flexiride.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterRepository {

    private var apiInterface: ApiInterface?=null

    init {
        apiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)
    }



    fun createUser(signupRequest: SignupRequest):LiveData<SignupResponse>{
        val data = MutableLiveData<SignupResponse>()

        apiInterface?.doSignUp(signupRequest)?.enqueue(object : Callback<SignupResponse>{
            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                val res = response.body()
                if (response.code() == 200 && res!=null){
                    data.value = res
                    Log.d("res", res.id.toString())
                }else{
                    data.value = null
                }
            }
        })

        return data

    }


}