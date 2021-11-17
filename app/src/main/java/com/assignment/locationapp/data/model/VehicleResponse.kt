package com.assignment.locationapp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VehicleResponse(
    @SerializedName("poiList")
    @Expose
    val poiList: List<PoiModel>?
)