package com.herts.flexiride.model

import java.io.Serializable

class SignInRequest : Serializable {

    var email: String = ""
    var password: String = ""
}