package com.worldline.pointsofinterest.data

interface NetworkStatusChecker {
    fun hasInternetConnection(): Boolean
}