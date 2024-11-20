package com.gurmeet.alllanguagenewsapp.utils

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.gurmeet.alllanguagenewsapp.Application

class NetworkViewModel(application: Application) : AndroidViewModel(application) {

    private val networkConnectivityObserver = NetworkConnectivityObserver(application)

    val isConnected: LiveData<Boolean> get() = networkConnectivityObserver.isConnected

    fun startMonitoring() {
        networkConnectivityObserver.startMonitoring()
    }

    fun stopMonitoring() {
        networkConnectivityObserver.stopMonitoring()
    } }