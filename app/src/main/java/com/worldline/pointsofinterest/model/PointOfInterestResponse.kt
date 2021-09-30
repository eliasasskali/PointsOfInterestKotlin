package com.worldline.pointsofinterest.model

data class PointOfInterestResponse(
    val pointsOfInterest : List<PointOfInterest>,
    val success: Boolean
) {
}