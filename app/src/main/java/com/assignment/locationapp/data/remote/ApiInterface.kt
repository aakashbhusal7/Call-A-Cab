package com.assignment.locationapp.data.remote

import com.assignment.locationapp.data.model.VehicleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("/")
    suspend fun getVehicleData(
        @Query("p1Lat") sourceLatitude: Double,
        @Query("p1Lon") sourceLongitude: Double,
        @Query("p2Lat") destLatitude: Double,
        @Query("p2Lon") destLongitude: Double
    ): Response<VehicleResponse>
}