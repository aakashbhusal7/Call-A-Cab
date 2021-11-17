package com.assignment.locationapp.domain.interactor

import androidx.lifecycle.LiveData
import com.assignment.locationapp.data.model.VehicleResponse
import com.assignment.locationapp.data.repository.ApiRepository
import com.assignment.locationapp.ext.Constants
import com.assignment.locationapp.ext.Resource

class LoadVehicleUseCaseImpl(
    private val repository: ApiRepository):LoadVehicleUseCase{

    override fun invoke(): LiveData<Resource<VehicleResponse>> =
        repository.loadVehicleData(
            Constants.STARTING_LAT,
            Constants.STARTING_LONG,
            Constants.END_LAT,
            Constants.END_LONG
        )
}