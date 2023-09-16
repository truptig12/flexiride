package com.herts.flexiride.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.herts.flexiride.data.repository.CarRepository
import com.herts.flexiride.data.request.AddAvailabilityRequest
import com.herts.flexiride.data.request.AddPackageRequest
import com.herts.flexiride.data.request.AddCarRequest
import com.herts.flexiride.data.response.AddCarResponse
import okhttp3.MultipartBody

class CarViewModel(application: Application) : AndroidViewModel(application) {

    private var carRepository: CarRepository? = null
    var createPostLiveData: LiveData<AddCarResponse>? = null
    var createPackageLiveData: LiveData<AddCarResponse>? = null
    var createAvailabilityLiveData: LiveData<AddCarResponse>? = null
    var createPhotosLiveData: LiveData<AddCarResponse>? = null

    init {
        carRepository = CarRepository()
        createPostLiveData = MutableLiveData()
        createPackageLiveData = MutableLiveData()
        createAvailabilityLiveData = MutableLiveData()
        createPhotosLiveData = MutableLiveData()
    }

    fun addCar(token: String, addCarRequest: AddCarRequest) {
        createPostLiveData = carRepository?.addCar(token, addCarRequest)
    }

    fun addPackage(token: String,addPackageRequest: AddPackageRequest) {
        createPostLiveData = carRepository?.addPackage(token,addPackageRequest)
    }

    fun addAvailability(token:String, addAvailabilityRequest: AddAvailabilityRequest) {
        createAvailabilityLiveData = carRepository?.addAvailability(token,addAvailabilityRequest)
    }

    fun addCarPhotos(
        token:String,
        i: Int,
        body1: MultipartBody.Part,
        body2: MultipartBody.Part,
        body3: MultipartBody.Part,
        body4: MultipartBody.Part

    ) {
        createPhotosLiveData = carRepository?.addCarPhotos(token,i, body1, body2, body3, body4)
    }

}