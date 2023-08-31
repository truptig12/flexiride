package com.herts.flexiride.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UserCarDetailsResponse : Serializable {
    @SerializedName("responseCode")
    var responseCode: Int? = null

    @SerializedName("responseDescription")
    var responseDescription: String? = null

    @SerializedName("id")
    var id: Int? = null

    @SerializedName("carList")
    @Expose
    var carList: List<CarList>? = null

}

class CarList : Serializable {

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

    @SerializedName("fromDate")
    var fromDate: String? = null

    @SerializedName("toDate")
    var toDate: String? = null

    var verified: Boolean = false

}