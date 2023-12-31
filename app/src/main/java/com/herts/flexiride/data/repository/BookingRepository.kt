package com.herts.flexiride.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.herts.flexiride.data.request.AddAvailabilityRequest
import com.herts.flexiride.data.request.AddBookingRequest
import com.herts.flexiride.data.request.AddPackageRequest
import com.herts.flexiride.data.request.AddCarRequest
import com.herts.flexiride.data.response.AddCarResponse
import com.herts.flexiride.data.response.CarList
import com.herts.flexiride.data.response.SignupResponse
import com.herts.flexiride.network.ApiClient
import com.herts.flexiride.network.ApiInterface
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookingRepository {

    private var apiInterface: ApiInterface? = null

    init {
        apiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)
    }


    fun addBooking( token:String,addBookingRequest: AddBookingRequest): LiveData<SignupResponse> {
        val data = MutableLiveData<SignupResponse>()

        apiInterface?.addBooking(token,addBookingRequest)?.enqueue(object : Callback<SignupResponse> {
            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(
                call: Call<SignupResponse>,
                response: Response<SignupResponse>
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

    fun getCarDetails(token:String,i: Int): LiveData<CarList> {
        val data = MutableLiveData<CarList>()

        apiInterface?.getCarDetails(token,i)
            ?.enqueue(object : Callback<CarList> {
                override fun onFailure(call: Call<CarList>, t: Throwable) {
                    data.value = null
                }

                override fun onResponse(
                    call: Call<CarList>,
                    response: Response<CarList>
                ) {
                    val res = response.body()
                    if (response.code() == 200 && res != null) {
                        data.value = res
                        Log.d("res", res.carId.toString())
                    } else {
                        data.value = null
                    }
                }
            })

        return data

    }

   /* fun addCarPhotos(
        i: Int,
        body1: MultipartBody.Part,
        body2: MultipartBody.Part,
        body3: MultipartBody.Part,
        body4: MultipartBody.Part
    ): LiveData<AddCarResponse> {
        val data = MutableLiveData<AddCarResponse>()

        apiInterface?.addCarPhotos(i, body1, body2, body3, body4)
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

    }*/


}