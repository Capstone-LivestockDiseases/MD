package com.example.capstone_livestockcare.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capstone_livestockcare.data.UserRepository
import com.example.capstone_livestockcare.data.preference.UserModel
import com.example.capstone_livestockcare.data.response.LoginResponse
import com.example.capstone_livestockcare.data.response.RegisterResponse

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    var loginResult : MutableLiveData<LoginResponse> = repository.loginResult
    var isLoading: LiveData<Boolean> = repository.isLoading

    fun login(email: String, password: String) {
        return repository.login(email, password)
    }

    suspend fun saveSession(user: UserModel) {
        repository.saveSession(user)
    }
}