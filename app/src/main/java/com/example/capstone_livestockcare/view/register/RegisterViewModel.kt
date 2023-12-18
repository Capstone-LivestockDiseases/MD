package com.example.capstone_livestockcare.view.register

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.capstone_livestockcare.data.UserRepository
import com.example.capstone_livestockcare.data.response.RegisterResponse

class RegisterViewModel(private var repository: UserRepository) : ViewModel() {

    suspend fun register(username: String, email: String, password: String, confirmPassword: String): RegisterResponse {
//        Log.d("Erorr nih bang", "${username} ${email} ${password}  ${confirmPassword}")
        return repository.register(username, email, password, confirmPassword)
    }
}