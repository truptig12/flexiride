package com.herts.flexiride.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.herts.flexiride.data.request.AddAvailabilityRequest
import com.herts.flexiride.data.request.AddPackageRequest
import com.herts.flexiride.data.request.AddCarRequest
import com.herts.flexiride.data.response.AddCarResponse
import com.herts.flexiride.network.ApiClient
import com.herts.flexiride.network.ApiInterface
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CarRepository {

    private var apiInterface: ApiInterface? = null

    init {
        apiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)
    }


    fun addCar(token: String,addCarRequest: AddCarRequest): LiveData<AddCarResponse> {
        val data = MutableLiveData<AddCarResponse>()

        apiInterface?.addCar(token,addCarRequest)?.enqueue(object : Callback<AddCarResponse> {
            override fun onFailure(call: Call<AddCarResponse>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(
                call: Call<AddCarResponse>,
                response: Response<AddCarResponse>
            ) {
                val res = response.body()
                if (response.code() == 200 && res != null) {
                    data.value = res
                    Log.d("res", res.id.toString())
                } else {
                    data.value = null
                }
            }
        })

        return data

    }

    fun addPackage(token: String,addPackageRequest: AddPackageRequest): LiveData<AddCarResponse> {
        val data = MutableLiveData<AddCarResponse>()

        apiInterface?.addPackage(token,addPackageRequest)
            ?.enqueue(object : Callback<AddCarResponse> {
                override fun onFailure(call: Call<AddCarResponse>, t: Throwable) {
                    data.value = null
                }

                override fun onResponse(
                    call: Call<AddCarResponse>,
                    response: Response<AddCarResponse>
                ) {
                    val res = response.body()
                    if (response.code() == 200 && res != null) {
                        data.value = res
                        Log.d("res", res.id.toString())
                    } else {
                        data.value = null
                    }
                }
            })

        return data

    }

    fun addAvailability(token:String, addAvailabilityRequest: AddAvailabilityRequest): LiveData<AddCarResponse> {
        val data = MutableLiveData<AddCarResponse>()

        apiInterface?.addAvailability(token,addAvailabilityRequest)
            ?.enqueue(object : Callback<AddCarResponse> {
                override fun onFailure(call: Call<AddCarResponse>, t: Throwable) {
                    data.value = null
                }

                override fun onResponse(
                    call: Call<AddCarResponse>,
                    response: Response<AddCarResponse>
                ) {
                    val res = response.body()
                    if (response.code() == 200 && res != null) {
                        data.value = res
                        Log.d("res", res.id.toString())
                    } else {
                        data.value = null
                    }
                }
            })

        return data

    }

    fun addCarPhotos(
        token:String,
        i: Int,
        body1: MultipartBody.Part,
        body2: MultipartBody.Part,
        body3: MultipartBody.Part,
        body4: MultipartBody.Part
    ): LiveData<AddCarResponse> {
        val data = MutableLiveData<AddCarResponse>()

        apiInterface?.addCarPhotos(token,i, body1, body2, body3, body4)
            ?.enqueue(object : Callback<AddCarResponse> {
                override fun onFailure(call: Call<AddCarResponse>, t: Throwable) {
                    data.value = null
                }

                override fun onResponse(
                    call: Call<AddCarResponse>,
                    response: Response<AddCarResponse>
                ) {
                    val res = response.body()
                    if (response.code() == 200 && res != null) {
                        data.value = res
                        Log.d("res", res.id.toString())
                    } else {
                        data.value = null
                    }
                }
            })

        return data

    }


}