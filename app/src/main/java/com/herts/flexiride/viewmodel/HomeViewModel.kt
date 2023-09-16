package com.herts.flexiride.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.herts.flexiride.data.repository.DashboardRepository
import com.herts.flexiride.data.repository.HomeRepository
import com.herts.flexiride.data.request.SignupRequest
import com.herts.flexiride.data.response.SignupResponse
import com.herts.flexiride.data.response.UserCarDetailsResponse

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private var homeRepository: HomeRepository? = null
    var postModelListLiveData: LiveData<UserCarDetailsResponse>? = null


    init {
        homeRepository = HomeRepository()
        postModelListLiveData = MutableLiveData()
    }


    fun fetchAllCars(token:String,city: String, startDate: String, endDate: String) {
        postModelListLiveData = homeRepository?.fetchAllCars(token,city, startDate, endDate)
    }

}