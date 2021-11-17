package com.assignment.locationapp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoordinateModel(
    @SerializedName("latitude")
    @Expose
    val latitude: Double?,
    @SerializedName("longitude")
    @Expose
    val longitude: Double?
)