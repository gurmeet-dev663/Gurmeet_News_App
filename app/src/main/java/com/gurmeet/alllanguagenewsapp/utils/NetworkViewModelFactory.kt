package com.gurmeet.alllanguagenewsapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gurmeet.alllanguagenewsapp.Application

class NetworkViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NetworkViewModel::class.java)) {
            return NetworkViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    } }