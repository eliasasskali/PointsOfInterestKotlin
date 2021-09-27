package com.worldline.pointsofinterest.data

import com.google.gson.annotations.SerializedName

data class PointsOfInterestListNetworkDto(
    @SerializedName("list") val list: List<PointOfInterestNetworkSmallDto>
) {
}