package com.example.capstone_livestockcare.data.remote

import com.example.capstone_livestockcare.data.model.UserLogin
import com.example.capstone_livestockcare.data.model.UserRegister
import com.example.capstone_livestockcare.data.response.LoginResponse
import com.example.capstone_livestockcare.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/Json")
    @POST("register")
    suspend fun register(
        @Body request: UserRegister
    ): RegisterResponse

    @Headers("Content-Type: application/Json")
    @POST("login")
    fun login(
        @Body request: UserLogin
    ): Call <LoginResponse>
}