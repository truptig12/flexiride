package com.herts.flexiride.data.request

import java.io.Serializable

class AddBookingRequest : Serializable {
    var userId: Int = 0
    var bookedUserId:Int =0
    var carId: Int = 0
    var fromDate: String = ""
    var toDate: String = ""
    var bookingAmount: Int = 0

}