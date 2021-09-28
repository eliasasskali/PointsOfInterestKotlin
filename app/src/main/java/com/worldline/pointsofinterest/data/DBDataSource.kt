package com.worldline.pointsofinterest.data

import com.worldline.pointsofinterest.model.PointOfInterest
import io.realm.Case
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

        realm.beginTransaction()
        realm.insertOrUpdate(pointsOfInterest.map { it.toModel() })
        realm.commitTransaction()
    }

    fun insertOrUpdatePointOfInterest(pointOfInterest: PointOfInterest) {
        val realm = Realm.getDefaultInstance()

        realm.beginTransaction()
        realm.insertOrUpdate(pointOfInterest.toModel())
        realm.commitTransaction()
    }

    fun getPointOfInterest(id: Int) : PointOfInterest {
        val realm = Realm.getDefaultInstance()

        return realm
            .where<PointOfInterestDbDto>()
            .equalTo("id", id)
            .findFirst()
            ?.toModel()?: PointOfInterest(id = 0, title = "", geocoordinates = "")
    }

    fun filterWithText(searchText: String) : List<PointOfInterest> {
        val realm = Realm.getDefaultInstance()

        return realm
            .where<PointOfInterestDbDto>()
            .contains("title", searchText, Case.INSENSITIVE)
            .findAll()
            ?.map { it.toModel() }
            ?: listOf()
    }

    fun databaseIsEmpty() : Boolean {
        val realm = Realm.getDefaultInstance()
        return realm.isEmpty
    }
}