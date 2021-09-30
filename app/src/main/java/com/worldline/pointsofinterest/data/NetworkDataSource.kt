package com.worldline.pointsofinterest.data

import com.worldline.pointsofinterest.model.PointOfInterest
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkDataSource {
    private fun getRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://t21services.herokuapp.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    suspend fun fetchPointsOfInterest(): List<PointOfInterest> {
        val pointsOfInterestSmallNetwork = getRetrofit().create(APIService::class.java).fetchPointsOfInterest("points")
        return pointsOfInterestSmallNetwork.list.map { it.toModel() }
    }

    suspend fun fetchPointOfInterestDetail(id: Int) : PointOfInterest {
        return getRetrofit().create(APIService::class.java).fetchPointOfInterestDetail("points/$id").toModel()
    }
}