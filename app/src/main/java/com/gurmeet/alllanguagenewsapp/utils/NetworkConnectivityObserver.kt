package com.gurmeet.alllanguagenewsapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class NetworkConnectivityObserver(context: Context) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean> get() = _isConnected

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            _isConnected.postValue(true) // Internet is available
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            _isConnected.postValue(false) // Internet is lost
        }
    }

    fun startMonitoring() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val request = android.net.NetworkRequest.Builder().build()
            connectivityManager.registerNetworkCallback(request, networkCallback)
        } else {
            // Fallback for devices below API level 21 (not covered here)
            _isConnected.postValue(false)
        }
    }

    fun stopMonitoring() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}
