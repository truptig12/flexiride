package com.herts.flexiride.data.response

import com.google.gson.annotations.SerializedName

class SignupResponse {
    @SerializedName("responseCode")
    var responseCode: Int? = null

    @SerializedName("responseDescription")
    var responseDescription: String? = null

    @SerializedName("id")
    var id: Int? = null
}