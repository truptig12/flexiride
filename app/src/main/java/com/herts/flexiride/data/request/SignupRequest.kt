package com.herts.flexiride.data.request

import java.io.Serializable

class SignupRequest : Serializable {
    var firstName: String = ""
    var lastName: String = ""
    var email: String = ""
    var password: String = ""
    var roleId =1
}