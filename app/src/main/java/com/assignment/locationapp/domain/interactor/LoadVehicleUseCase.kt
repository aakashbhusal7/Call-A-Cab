package com.assignment.locationapp.domain.interactor

import androidx.lifecycle.LiveData
import com.assignment.locationapp.data.model.VehicleResponse
import com.assignment.locationapp.ext.Resource

interface LoadVehicleUseCase {
    operator fun invoke():LiveData<Resource<VehicleResponse>>
}