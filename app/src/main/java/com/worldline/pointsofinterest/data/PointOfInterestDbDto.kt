package com.worldline.pointsofinterest.data

import com.worldline.pointsofinterest.model.PointOfInterest
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class PointOfInterestDbDto(
    @PrimaryKey var id: Int = 0,
    @Required var title: String = "",
    @Required var geocoordinates: String = "",
    var desc: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var transport: String? = null,
    var address: String? = null
) : RealmObject() {
    fun toModel() : PointOfInterest {
        return PointOfInterest(
            id = id,
            title = title,
            geocoordinates = geocoordinates,
            desc = desc?:"",
            email = email?:"",
            phone = phone?:"",
            transport = transport?:"",
            address = address?:""
        )
    }
}