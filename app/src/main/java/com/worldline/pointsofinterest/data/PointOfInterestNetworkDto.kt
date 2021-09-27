package com.worldline.pointsofinterest.data

import com.google.gson.annotations.SerializedName
import com.worldline.pointsofinterest.model.PointOfInterest

data class PointOfInterestNetworkDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("geocoordinates") val geocoordinates: String,
    @SerializedName("description") val description: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("phone") val phone: String = "",
    @SerializedName("transport") val transport: String = "",
    @SerializedName("address") val address: String = ""
) {
    fun toModel() : PointOfInterest {
        return PointOfInterest(
            id = id,
            title = title,
            geocoordinates = geocoordinates,
            desc = description?:"",
            email = email?:"",
            phone = phone?:"",
            transport = transport?:"",
            address = address?:""
        )
    }
}