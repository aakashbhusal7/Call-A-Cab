package com.assignment.locationapp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PoiModel(
    @SerializedName("id")
    @Expose
    val vehicleId: Int?,
    @SerializedName("coordinate")
    @Expose
    val coordinateData: CoordinateModel?,
    @SerializedName("fleetType")
    @Expose
    val vehicleType: String?,
    @SerializedName("heading")
    @Expose
    val heading: Double?
)