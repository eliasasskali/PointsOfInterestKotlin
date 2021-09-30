package com.worldline.pointsofinterest.data

import android.app.AlertDialog
import android.os.Build
import androidx.annotation.RequiresApi
import com.worldline.pointsofinterest.model.PointOfInterest
import com.worldline.pointsofinterest.model.PointOfInterestResponse

class PointsOfInterestRepository(private val networkStatusChecker: NetworkStatusChecker) {
    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun fetchPointsOfInterest(fromNetwork: Boolean = true) : PointOfInterestResponse {
        // Nothing to show, internet connection needed
        if (!networkStatusChecker.hasInternetConnection() && databaseIsEmpty()) {
            println("No network connection.")
        }

        // Download POIs (small) from internet and insert them in db
        if (fromNetwork && databaseIsEmpty() && networkStatusChecker.hasInternetConnection()) {
            val pointsOfInterestSmall = NetworkDataSource().fetchPointsOfInterest()
            DBDataSource().insertPointsOfInterest(pointsOfInterestSmall)
            return PointOfInterestResponse(pointsOfInterest = pointsOfInterestSmall, success = true)
        }

        // Query POIs from the database
        val pointsOfInterestDb = DBDataSource().fetchPointsOfInterest()
        return PointOfInterestResponse(pointsOfInterest = pointsOfInterestDb, success = pointsOfInterestDb.isNotEmpty())
    }

    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun fetchPointOfInterestDetail(id: Int, fromNetwork: Boolean = true) : PointOfInterest {
        // Download POI detail from internet and return it
        if (fromNetwork && networkStatusChecker.hasInternetConnection()) {
            val pointOfInterest = NetworkDataSource().fetchPointOfInterestDetail(id)
            DBDataSource().insertOrUpdatePointOfInterest(pointOfInterest)
            return pointOfInterest
        }

        // Fetch POI from database
        return DBDataSource().getPointOfInterest(id)
    }

    fun filterWithText(searchText: String) : List<PointOfInterest> {
        return DBDataSource().filterWithText(searchText)
    }

    private fun databaseIsEmpty() : Boolean {
        return DBDataSource().databaseIsEmpty()
    }
}