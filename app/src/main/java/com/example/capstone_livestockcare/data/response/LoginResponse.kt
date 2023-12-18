package com.example.capstone_livestockcare.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("LoginResult")
    val LoginResult: LoginResult? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class LoginResult(

    @field:SerializedName("Id")
    val userId: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("token")
    val token: String? = null
)