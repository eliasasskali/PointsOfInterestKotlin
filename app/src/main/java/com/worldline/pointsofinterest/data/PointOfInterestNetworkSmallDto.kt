package com.worldline.pointsofinterest.data

import com.google.gson.annotations.SerializedName
import com.worldline.pointsofinterest.model.PointOfInterest

data class PointOfInterestNetworkSmallDto(
    @SerializedName("id") var id: Int,
    @SerializedName("title") var title: String,
    @SerializedName("geocoordinates") var geocoordinates: String
) {
    fun toModel() : PointOfInterest {
        return PointOfInterest(
            id = id,
            title = title,
            geocoordinates = geocoordinates
        )
    }
}