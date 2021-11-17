package com.assignment.locationapp.util

import com.assignment.locationapp.data.model.CoordinateModel
import com.assignment.locationapp.data.model.PoiModel
import com.assignment.locationapp.data.model.VehicleResponse

//make mockup of the expected data to compare it with what is to be actually presented

object DummyObject {

    //for api data
    val remoteData: VehicleResponse =
        VehicleResponse(
            listOf(
                PoiModel(
                    1,
                    CoordinateModel(
                        12.23444,
                        66.44333
                    ),
                    "pooling",
                    12.67744413
                )
            )
        )

}