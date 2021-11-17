package com.assignment.locationapp.ui

import androidx.lifecycle.MutableLiveData
import com.assignment.locationapp.BaseTest
import com.assignment.locationapp.data.repository.ApiRepository
import com.assignment.locationapp.ext.Constants
import com.assignment.locationapp.ext.Resource
import com.assignment.locationapp.ui.viewmodel.VehicleViewModel
import com.assignment.locationapp.util.DummyObject
import com.jraska.livedata.test
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@ExperimentalCoroutinesApi
class VehicleViewModelTest : BaseTest() {
    override fun preTest() {
        testObject = VehicleViewModel(giphyApiRepository)
    }

    // Subject under test
    private lateinit var testObject: VehicleViewModel

    //relaxed mocks to return a default value for each function
    // no need to describe behavior of each method
    @RelaxedMockK
    private lateinit var giphyApiRepository: ApiRepository

    //test to check api call for vehicle lists
    @Test
    fun getVehicleData() {
        val expected = MutableLiveData(Resource.success(DummyObject.remoteData))
        every {
            giphyApiRepository.loadVehicleData(
                Constants.STARTING_LAT,
                Constants.STARTING_LONG,
                Constants.END_LAT,
                Constants.END_LONG
            )
        }.returns(expected)
        testObject.start()
        //use of jraska library for easy testing of live data
        val obs = testObject.remoteVehicles.test()
        obs.assertHasValue()
            .assertValue {
                it.data == (DummyObject.remoteData)
            }
    }

}