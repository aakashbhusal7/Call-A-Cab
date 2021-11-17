package com.assignment.locationapp.ui.viewmodel

import androidx.lifecycle.*
import com.assignment.locationapp.data.model.VehicleResponse
import com.assignment.locationapp.data.repository.ApiRepository
import com.assignment.locationapp.domain.interactor.LoadVehicleUseCase
import com.assignment.locationapp.ext.Constants
import com.assignment.locationapp.ext.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehicleViewModel @Inject constructor(
    private val getVehicleDataUseCase: LoadVehicleUseCase
) : ViewModel() {

    private val _invoke = MutableLiveData<Boolean>()

    //for mocking purpose in unit test
    fun start() {
        _invoke.value = true
    }

    //store required values in the variables for db and api

    var remoteVehiclesInitial = _invoke.switchMap {
        getVehicleDataUseCase.invoke()
    }

    //use this variable to compare the data with the mockup we create with {@DummyObject}
    val remoteVehicles: LiveData<Resource<VehicleResponse>> = remoteVehiclesInitial

    fun loadVehicleData():LiveData<Resource<VehicleResponse>> {
       return getVehicleDataUseCase.invoke()
    }

}