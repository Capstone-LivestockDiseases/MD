package com.example.capstone_livestockcare.data.di

import android.content.Context
import com.example.capstone_livestockcare.data.UserRepository
import com.example.capstone_livestockcare.data.preference.UserPreference
import com.example.capstone_livestockcare.data.preference.dataStore
import com.example.capstone_livestockcare.data.remote.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun injectionRepository(context: Context): UserRepository = runBlocking  {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = pref.getSession().first()
        val apiService = ApiConfig.getApiService(user.token)
        UserRepository.getInstance(apiService, pref)
    }
}