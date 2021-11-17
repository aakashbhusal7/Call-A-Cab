package com.assignment.locationapp.data.remote

import com.assignment.locationapp.ext.NoInternetException
import com.assignment.locationapp.ext.Resource
import org.json.JSONObject
import retrofit2.Response
import timber.log.Timber

abstract class BaseDataSource {
    /**function to encapsulate retrofit response in a Resource data class
     *we can catch the errors in a much easy way by doing this
     *and also handle the result in a proper way
     */
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            } else if (response.code() == 422) {
            }
            val body = response.errorBody()
            var errorString = ""
            if (body != null) {
                val jsonErrorBody = JSONObject(response.errorBody()!!.charStream().readText())
                errorString = jsonErrorBody.getString("message")
            }
            return error(errorString)
        } catch (ex: NoInternetException) {
            return Resource.noInternet(ex.message ?: ex.toString())
        } catch (e: Exception) {
            return Resource.noInternet("Failed to connect to server")
        }
    }

    private fun <T> error(message: String): Resource<T> {
        Timber.d(message)
        return Resource.error(message, null)
    }
}
