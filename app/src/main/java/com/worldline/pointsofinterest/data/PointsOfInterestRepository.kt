package com.worldline.pointsofinterest.data

import com.worldline.pointsofinterest.model.PointOfInterest

class PointsOfInterestRepository {
    suspend fun fetchPointsOfInterest() : List<PointOfInterest> {
        val pointsOfInterest = NetworkDataSource().fetchPointsOfInterest()
        DBDataSource().insertPointsOfInterest(pointsOfInterest)
        return pointsOfInterest
    }

    fun fetchPointsOfInterestDb() : List<PointOfInterest> {
        return DBDataSource().fetchPointsOfInterest()
    }

    suspend fun fetchPointOfInterestDetail(id: Int, fromNetwork: Boolean = true) : PointOfInterest {
        if (fromNetwork) {
            return NetworkDataSource().fetchPointOfInterestDetail(id).toModel()
        }
        return DBDataSource().getPointOfInterest(id)
    }


    fun filterWithText(searchText: String) : List<PointOfInterest> {
        return DBDataSource().filterWithText(searchText)
    }
}