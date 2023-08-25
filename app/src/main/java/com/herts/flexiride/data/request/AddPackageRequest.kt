package com.herts.flexiride.data.request

import java.io.Serializable

class AddPackageRequest : Serializable {
    var userId: Int = 0
    var carId: Int = 0
    var fareAmount: Long = 0
    var duration: String = ""


}