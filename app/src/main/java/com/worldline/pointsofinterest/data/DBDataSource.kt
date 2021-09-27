package com.worldline.pointsofinterest.data

import com.worldline.pointsofinterest.model.PointOfInterest
import io.realm.Realm
import io.realm.kotlin.where

class DBDataSource {
    fun fetchPointsOfInterest() : List<PointOfInterest> {
        val realm = Realm.getDefaultInstance()
        val pointsOfInterestDto = realm.where<PointOfInterestDbDto>().findAll()
        return pointsOfInterestDto.map {
            it.toModel()
        }
    }

    fun insertPointsOfInterest(pointsOfInterest: List<PointOfInterest>) {
        val realm = Realm.getDefaultInstance()

        val pointsOfInterestDB = pointsOfInterest.map { it.toModel() }

        realm.beginTransaction()
        realm.insertOrUpdate(pointsOfInterestDB)
        realm.commitTransaction()
    }

    fun getPointOfInterest(id: Int) : PointOfInterest? {
        val realm = Realm.getDefaultInstance()

        return realm
            .where<PointOfInterestDbDto>()
            .equalTo("id", id)
            .findFirst()
            ?.toModel()
    }

    fun filterWithText(searchText: String) : List<PointOfInterest> {
        val realm = Realm.getDefaultInstance()

        return realm
            .where<PointOfInterestDbDto>()
            .contains("title", searchText)
            .findAll()
            ?.map { it.toModel() }
            ?: listOf()
    }
}