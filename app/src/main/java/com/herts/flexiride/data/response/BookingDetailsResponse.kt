package com.herts.flexiride.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class BookingDetailsResponse : Serializable {
    @SerializedName("responseCode")
    var responseCode: Int? = null

    @SerializedName("responseDescription")
    var responseDescription: String? = null

    @SerializedName("id")
    var id: Int? = null

    @SerializedName("bookingList")
    @Expose
    var bookingList: List<BookingList>? = null

}

class BookingList : Serializable {

    @SerializedName("bookingId")
    var bookingId: Int? = null

    @SerializedName("userId")
    var userId: Int? = null

    @SerializedName("bookedUserId")
    var bookedUserId: Int? = null

    @SerializedName("carId")
    var carId: Int? = null

    @SerializedName("fromDate")
    var fromDate: String? = null

    @SerializedName("toDate")
    var toDate: String? = null

    @SerializedName("bookingAmount")
    var bookingAmount: String? = null

    @SerializedName("booked")
    var booked: Int? = null

    @SerializedName("car")
    var car: Car? = null

    @SerializedName("user")
    var user: User? = null

}

class Car : Serializable {

    @SerializedName("carId")
    var carId: Int? = null

    @SerializedName("userId")
    var userId: Int? = null

    @SerializedName("brand")
    var brand: String? = null

    @SerializedName("model")
    var model: String? = null

    @SerializedName("color")
    var color: String? = null

    @SerializedName("manOfYear")
    var manOfYear: String? = null

    @SerializedName("noOfSeats")
    var noOfSeats: String? = null

    @SerializedName("city")
    var city: String? = null

    @SerializedName("licencePlate")
    var licencePlate: String? = null

    @SerializedName("fuelType")
    var fuelType: String? = null

    @SerializedName("gearbox")
    var gearbox: String? = null

    @SerializedName("engine")
    var engine: String? = null

    @SerializedName("image1")
    var image1: String? = null

    @SerializedName("image2")
    var image2: String? = null

    @SerializedName("image3")
    var image3: String? = null

    @SerializedName("image4")
    var image4: String? = null

    @SerializedName("fareAmount")
    var fareAmount: String? = null


}

class User : Serializable {

    @SerializedName("user_id")
    var user_id: Int? = null

    @SerializedName("role_id")
    var role_id: Int? = null

    @SerializedName("first_name")
    var first_name: String? = null

    @SerializedName("last_name")
    var last_name: String? = null

    @SerializedName("email")
    var email: String? = null

    @SerializedName("carList")
    var carList: String? = null
}