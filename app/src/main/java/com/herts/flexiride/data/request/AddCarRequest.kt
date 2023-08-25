package com.herts.flexiride.data.request

import java.io.Serializable

class AddCarRequest:Serializable {
    var userId:Int =0
    var brand:String =""
    var model:String =""
    var color:String =""
    var manOfYear:String =""
    var noOfSeats:String =""
    var country:String =""
    var city:String =""
    var licencePlate:String =""
    var fuelType:String =""
    var gearbox:String =""
    var engine:String =""

}