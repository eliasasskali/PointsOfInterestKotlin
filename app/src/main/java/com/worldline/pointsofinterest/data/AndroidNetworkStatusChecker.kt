package com.worldline.pointsofinterest.data

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * Checks the Internet connection and performs an action if it's active.
 */
class AndroidNetworkStatusChecker(private val connectivityManager: ConnectivityManager?) : NetworkStatusChecker {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun hasInternetConnection(): Boolean {
        val network = connectivityManager?.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
    }
}