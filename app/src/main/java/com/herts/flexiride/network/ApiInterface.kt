package com.herts.flexiride.network

import com.herts.flexiride.data.request.AddAvailabilityRequest
import com.herts.flexiride.data.request.AddBookingRequest
import com.herts.flexiride.data.request.AddPackageRequest
import com.herts.flexiride.data.request.AddCarRequest
import com.herts.flexiride.model.SignInRequest
import com.herts.flexiride.data.request.SignupRequest
import com.herts.flexiride.data.response.AddCarResponse
import com.herts.flexiride.data.response.BookingDetailsResponse
import com.herts.flexiride.data.response.CarList
import com.herts.flexiride.data.response.SignInResponse
import com.herts.flexiride.data.response.SignupResponse
import com.herts.flexiride.data.response.UserCarDetailsResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @POST("addUser")
    fun doSignUp(@Body signUpRequest: SignupRequest): Call<SignupResponse>

    @POST("login")
    fun login(@Body signInRequest: SignInRequest): Call<SignInResponse>

    @POST("addCar")
    fun addCar(
        @Header("Authorization") token: String,
        @Body addCarRequest: AddCarRequest): Call<AddCarResponse>

    @POST("addPackage")
    fun addPackage(
        @Header("Authorization") token: String,
        @Body addPackageRequest: AddPackageRequest): Call<AddCarResponse>

    @POST("addAvailability")
    fun addAvailability(
        @Header("Authorization") token: String,
        @Body addAvailabilityRequest: AddAvailabilityRequest): Call<AddCarResponse>

    @Multipart
    @POST("updateCarImage")
    fun addCarPhotos(
        @Header("Authorization") token: String,
        @Part("id") id: Int,
        @Part img1: MultipartBody.Part?,
        @Part img2: MultipartBody.Part?,
        @Part img3: MultipartBody.Part?,
        @Part img4: MultipartBody.Part?
    ): Call<AddCarResponse>

    @POST("getCarByUserId")
    fun fetchAllCars(
        @Header("Authorization") token: String,
        @Query("id") id: Int): Call<UserCarDetailsResponse>

    @POST("findByUserId")
    fun fetchAllBookings(
        @Header("Authorization") token: String,
        @Query("id") id: Int): Call<BookingDetailsResponse>

    @POST("acceptBooking")
    fun acceptBooking(
        @Header("Authorization") token: String,
        @Query("id") id: Int): Call<SignupResponse>

    @POST("rejectBooking")
    fun rejectBooking(
        @Header("Authorization") token: String,
        @Query("id") id: Int): Call<SignupResponse>

    @POST("getAllAvailableCarOnDate")
    fun fetchAllCarsForUsers(
        @Header("Authorization") token: String,
        @Query("city") city: String,
        @Query("fromDate") startDate: String,
        @Query("toDate") endDate: String
    ): Call<UserCarDetailsResponse>


    @POST("addBooking")
    fun addBooking(
        @Header("Authorization") token: String,
        @Body addBookingRequest: AddBookingRequest): Call<SignupResponse>


    @POST("users/login")
    fun doSignIn(@Body signInRequest: SignInRequest): Call<SignupResponse>


    @DELETE("posts/{id}")
    fun deletePost(@Path("id") id: Int): Call<String>

    @Multipart
    @POST("image/image/")
    fun postImage(
        @Part image: MultipartBody.Part?,
        @Part("title") name: RequestBody?
    ): Call<ResponseBody>

    @POST("getCarById")
    fun getCarDetails(
        @Header("Authorization") token: String,
        @Query("id") id: Int): Call<CarList>


}