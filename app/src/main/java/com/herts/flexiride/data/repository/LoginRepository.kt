package com.herts.flexiride.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.herts.flexiride.data.response.SignInResponse
import com.herts.flexiride.model.SignInRequest
import com.herts.flexiride.data.response.SignupResponse
import com.herts.flexiride.network.ApiClient
import com.herts.flexiride.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository {

    private var apiInterface: ApiInterface? = null

    init {
        apiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)
    }


    /* fun loginUser(signInRequest: SignInRequest):LiveData<SignupResponse>{
         val data = MutableLiveData<SignupResponse>()

         apiInterface?.doSignIn(signInRequest)?.enqueue(object : Callback<SignupResponse>{
             override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                 data.value = null
             }

             override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                 val res = response.body()
                 if (response.code() == 201 && res!=null){
                     data.value = res
                     Log.d("res", res.id.toString())
                 }else{
                     data.value = null
                 }
             }
         })

         return data

     }*/

    fun loginUser(signInRequest: SignInRequest): LiveData<SignInResponse> {
        val data = MutableLiveData<SignInResponse>()

        apiInterface?.login(signInRequest)?.enqueue(object : Callback<SignInResponse> {
            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(
                call: Call<SignInResponse>,
                response: Response<SignInResponse>
            ) {
                val res = response.body()
                if (response.code() == 200 && res != null) {
                    data.value = res
                    Log.d("res:", res.token.toString())
                } else {
                    data.value = null
                }
            }
        })

        return data

    }


}