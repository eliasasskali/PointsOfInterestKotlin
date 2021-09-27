package com.worldline.pointsofinterest.data

import com.worldline.pointsofinterest.model.PointOfInterest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NetworkDataSource {
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://t21services.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    suspend fun fetchPointsOfInterest() : List<PointOfInterest> {
        val pointsOfInterestSmallNetwork = getRetrofit().create(APIService::class.java).fetchPointsOfInterest("points")
        val pois = pointsOfInterestSmallNetwork.list.map { it.toModel() }
        println(pois)
        return pois
    }

    suspend fun fetchPointOfInterestDetail(id: Int) : PointOfInterestNetworkDto {
        return getRetrofit().create(APIService::class.java).fetchPointOfInterestDetail("points/$id")
    }
}