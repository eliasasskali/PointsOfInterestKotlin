package com.worldline.pointsofinterest.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    suspend fun fetchPointsOfInterest(@Url url:String): PointsOfInterestListNetworkDto
    @GET
    suspend fun fetchPointOfInterestDetail(@Url url:String): PointOfInterestNetworkDto
}