package com.assignment.locationapp.ext

import org.json.JSONObject

/**
 * Class to encapsulate the repository responses according to their state
 */

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val statusCode: Int? = null,
    val errorBody: JSONObject? = null
) {
    //enum class to hold our possible defined states on an api call
    enum class Status {
        SUCCESS,
        ERROR,
        LOADING,
        NO_INTERNET
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(
            message: String,
            data: T?,
            statusCode: Int? = null,
            errorBody: JSONObject? = null
        ): Resource<T> {
            return Resource(Status.ERROR, data, message, statusCode, errorBody)
        }

        fun <T> noInternet(message: String, data: T? = null): Resource<T> {
            return Resource(Status.NO_INTERNET, data, message)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}
