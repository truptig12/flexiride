package com.herts.flexiride.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.herts.flexiride.data.repository.DashboardRepository
import com.herts.flexiride.data.request.SignupRequest
import com.herts.flexiride.data.response.BookingDetailsResponse
import com.herts.flexiride.data.response.SignupResponse
import com.herts.flexiride.data.response.UserCarDetailsResponse

class DashboardViewModel(application: Application): AndroidViewModel(application){

    private var homeRepository:DashboardRepository?=null
    var postBookingListLiveData:LiveData<BookingDetailsResponse>?=null
    var postModelListLiveData : LiveData<UserCarDetailsResponse>?=null
    var acceptBookingLiveData : LiveData<SignupResponse>?=null
    var rejectBookingLiveData : LiveData<SignupResponse>?=null


    init {
        homeRepository = DashboardRepository()
        postBookingListLiveData = MutableLiveData()
        postModelListLiveData = MutableLiveData()
        acceptBookingLiveData= MutableLiveData()
        rejectBookingLiveData= MutableLiveData()
    }



    fun fetchAllCars(i: Int) {
        postModelListLiveData = homeRepository?.fetchAllCars(i)
    }

    fun fetchAllBookings(i: Int) {
        postBookingListLiveData = homeRepository?.fetchAllBookings(i)
    }

    fun acceptBooking(i: Int) {
        acceptBookingLiveData = homeRepository?.acceptBooking(i)
    }

    fun rejectBooking(i: Int) {
        rejectBookingLiveData = homeRepository?.rejectBooking(i)
    }
}