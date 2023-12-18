package com.example.capstone_livestockcare.data.preference

data class UserModel(
    val token: String,
    val username: String,
    val userId: String,
    val isLogin: Boolean = false
)