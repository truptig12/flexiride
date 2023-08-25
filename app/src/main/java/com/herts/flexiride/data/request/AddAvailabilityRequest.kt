package com.herts.flexiride.data.request

import java.io.Serializable

class AddAvailabilityRequest : Serializable {
    var userId: Int = 0
    var carId: Int = 0
    var fromDate: String = ""
    var toDate: String = ""
    var fareAmount: String = ""
    var city: String = ""


}