package com.herts.flexiride.data.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class SignInResponse : Serializable {

    @SerializedName("token")
    var token: String? = null

    @SerializedName("user")
    var user: UserL? = null

}

class UserL : Serializable {

    @SerializedName("responseCode")
    var responseCode: Int? = null

    @SerializedName("responseDescription")
    var responseDescription: String? = null

    @SerializedName("id")
    var id: Int? = null

    @SerializedName("userId")
    var userId: Int? = null

    @SerializedName("roleId")
    var roleId: Int? = null

    @SerializedName("firstName")
    var firstName: String? = null

    @SerializedName("lastName")
    var lastName: String? = null

    @SerializedName("email")
    var email: String? = null

    @SerializedName("password")
    var password: String? = null
}