package com.worldline.pointsofinterest.model

import com.worldline.pointsofinterest.data.PointOfInterestDbDto

data class PointOfInterest(
    val id: Int,
    val title: String,
    val geocoordinates: String,
    val desc: String = "",
    val email: String = "",
    val phone: String = "",
    val transport: String = "",
    val address: String = ""
) {
    fun toModel() : PointOfInterestDbDto {
        return PointOfInterestDbDto(
            id = id,
            title = title,
            geocoordinates = geocoordinates,
            desc = desc?:"",
            email = email?:"",
            phone =phone?:"",
            transport = transport?:"",
            address = address?:""
        )
    }
}