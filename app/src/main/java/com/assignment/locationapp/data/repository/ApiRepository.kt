package com.assignment.locationapp.data.repository

import com.assignment.locationapp.data.remote.ApiInterface
import com.assignment.locationapp.data.remote.BaseDataSource
import com.assignment.locationapp.ext.performNetworkOperation
import javax.inject.Inject

//extra layer of abstraction for api calls
//database repository can be added here if there is any

class ApiRepository @Inject constructor(
    private val apiInterface: ApiInterface
) : BaseDataSource() {

    fun loadVehicleData(
        sourceLat: Double,
        sourceLong: Double,
        destLat: Double,
        destLong: Double
    ) = performNetworkOperation(
        networkCall = {
            getResult {
                apiInterface.getVehicleData(
                    sourceLat,
                    sourceLong,
                    destLat,
                    destLong
                )
            }
        }
    )
}