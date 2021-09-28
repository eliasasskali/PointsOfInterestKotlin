package com.worldline.pointsofinterest.data

class MockNetworkStatusChecker : NetworkStatusChecker {
    override fun hasInternetConnection(): Boolean {
        return false
    }
}