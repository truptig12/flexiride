package com.herts.flexiride.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.herts.flexiride.data.repository.BookingRepository
import com.herts.flexiride.data.repository.CarRepository
import com.herts.flexiride.data.request.AddAvailabilityRequest
import com.herts.flexiride.data.request.AddBookingRequest
import com.herts.flexiride.data.request.AddPackageRequest
import com.herts.flexiride.data.request.AddCarRequest
import com.herts.flexiride.data.response.AddCarResponse
import com.herts.flexiride.data.response.CarList
import com.herts.flexiride.data.response.SignupResponse
import okhttp3.MultipartBody

class BookingViewModel(application: Application) : AndroidViewModel(application) {

    private var carRepository: BookingRepository? = null
    var createPostLiveData: LiveData<SignupResponse>? = null
    var createPackageLiveData: LiveData<AddCarResponse>? = null
    var createAvailabilityLiveData: LiveData<CarList>? = null
    var createPhotosLiveData: LiveData<AddCarResponse>? = null

    init {
        carRepository = BookingRepository()
        createPostLiveData = MutableLiveData()
        createPackageLiveData = MutableLiveData()
        createAvailabilityLiveData = MutableLiveData()
        createPhotosLiveData = MutableLiveData()
    }

    fun addBooking( token:String,addBookingRequest: AddBookingRequest) {
        createPostLiveData = carRepository?.addBooking(token,addBookingRequest)
    }


    fun getCarDetails(token:String,i: Int) {
        createAvailabilityLiveData = carRepository?.getCarDetails(token,i)
    }

  /*  fun addCarPhotos(
        i: Int,
        body1: MultipartBody.Part,
        body2: MultipartBody.Part,
        body3: MultipartBody.Part,
        body4: MultipartBody.Part

    ) {
        createPhotosLiveData = carRepository?.addCarPhotos(i, body1,body2,body3,body4)
    }*/

}