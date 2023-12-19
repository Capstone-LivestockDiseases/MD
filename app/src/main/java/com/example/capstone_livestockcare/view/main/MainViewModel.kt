package com.example.capstone_livestockcare.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.capstone_livestockcare.data.UserRepository
import com.example.capstone_livestockcare.data.preference.UserModel

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    var isLoading: LiveData<Boolean> = repository.isLoading

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

}