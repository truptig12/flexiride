package com.herts.flexiride.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.herts.flexiride.data.request.SignupRequest
import com.herts.flexiride.data.response.SignupResponse
import com.herts.flexiride.data.response.UserCarDetailsResponse
import com.herts.flexiride.network.ApiClient
import com.herts.flexiride.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepository {

    private var apiInterface: ApiInterface? = null

    init {
        apiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)
    }


    fun fetchAllCars(
        city: String,
        startDate: String,
        endDate: String
    ): LiveData<UserCarDetailsResponse> {
        val data = MutableLiveData<UserCarDetailsResponse>()

        apiInterface?.fetchAllCarsForUsers(city, startDate, endDate)
            ?.enqueue(object : Callback<UserCarDetailsResponse> {

                override fun onFailure(call: Call<UserCarDetailsResponse>, t: Throwable) {
                    data.value = null
                }

                override fun onResponse(
                    call: Call<UserCarDetailsResponse>,
                    response: Response<UserCarDetailsResponse>
                ) {

                    val res = response.body()
                    if (response.code() == 200 && res != null) {
                        data.value = res
                    } else {
                        data.value = null
                    }

                }
            })

        return data

    }


}