package com.worldline.pointsofinterest.data

import com.worldline.pointsofinterest.model.PointOfInterest

class PointsOfInterestRepository {
    suspend fun fetchPointsOfInterest(fromNetwork: Boolean = true) : List<PointOfInterest> {
        // TODO: Check if there is internet connection and database has no elements
        if (fromNetwork) { //  && !databaseIsEmpty()
            return NetworkDataSource().fetchPointsOfInterest()
        }
        return DBDataSource().fetchPointsOfInterest()
    }

    suspend fun fetchPointOfInterestDetail(id: Int, fromNetwork: Boolean = true) : PointOfInterest {
        // TODO: Check if there is internet connection and element not already in db
        if (fromNetwork) { //&& !pointOfInterestInDatabase(id)
            val pointOfInterest = NetworkDataSource().fetchPointOfInterestDetail(id).toModel()
            DBDataSource().insertPointOfInterest(pointOfInterest)
            return pointOfInterest
        }
        return DBDataSource().getPointOfInterest(id)
    }

    fun filterWithText(searchText: String) : List<PointOfInterest> {
        return DBDataSource().filterWithText(searchText)
    }

    private fun databaseIsEmpty() : Boolean {
        return DBDataSource().databaseIsEmpty()
    }

    private fun pointOfInterestInDatabase(id: Int) : Boolean {
       return  DBDataSource().pointOfInterestInDatabase(id)
    }
}