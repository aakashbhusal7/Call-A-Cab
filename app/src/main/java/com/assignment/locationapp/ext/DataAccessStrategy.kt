package com.assignment.locationapp.ext

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers

fun <A> performNetworkOperation(
    networkCall: suspend () -> Resource<A>
): LiveData<Resource<A>> =
    liveData(Dispatchers.IO) {
        //initially looking for a data, so set to loading state right after the api call is triggered
        emit(Resource.loading())
        val responseStatus = networkCall.invoke()

        when (responseStatus.status) {
            Resource.Status.SUCCESS -> {
                emit(Resource.success(responseStatus.data!!))
            }
            Resource.Status.ERROR -> {
                emit(
                    Resource.error(
                        responseStatus.message!!,
                        responseStatus.data,
                        responseStatus.statusCode,
                        responseStatus.errorBody
                    )
                )
            }
            Resource.Status.NO_INTERNET -> {
                emit(
                    Resource.error(
                        responseStatus.message!!,
                        responseStatus.data,
                        responseStatus.statusCode,
                        responseStatus.errorBody
                    )
                )
            }
            else -> {
            }
        }

    }
