package com.example.capstone_livestockcare.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstone_livestockcare.data.model.UserLogin
import com.example.capstone_livestockcare.data.model.UserRegister
import com.example.capstone_livestockcare.data.preference.UserModel
import com.example.capstone_livestockcare.data.preference.UserPreference
import com.example.capstone_livestockcare.data.remote.ApiService
import com.example.capstone_livestockcare.data.response.LoginResponse
import com.example.capstone_livestockcare.data.response.RegisterResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
)  {
    private var _loginResult = MutableLiveData<LoginResponse>()
    var loginResult: MutableLiveData<LoginResponse> = _loginResult

    var _isLoading = MutableLiveData<Boolean>()
    var isLoading: LiveData<Boolean> = _isLoading

    suspend fun register( username: String, email: String, password: String, confirmPassword: String): RegisterResponse {
        val userRegister = UserRegister(username, email, password, confirmPassword)
        return apiService.register(userRegister)
    }


    fun login(email: String, password: String) {
        _isLoading.value = true
        val userLogin = UserLogin(email, password)
        val client = apiService.login(userLogin)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _loginResult.value = response.body()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("Repository", "error: ${t.message}")
            }
        })
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logOut()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun clearInstance() {
            instance = null
        }

        fun getInstance(apiService: ApiService, userPreference: UserPreference): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userPreference)
            }.also { instance = it }
    }
}