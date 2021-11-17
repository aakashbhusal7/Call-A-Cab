package com.assignment.locationapp.data.manager

import com.assignment.locationapp.data.local.SharedPrefHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceManager @Inject constructor(
    private val sharedPrefHelper: SharedPrefHelper
) {
//    val token: String?
//        get() {
//            val token = sharedPrefHelper.apiToken.get()
//            return if (token.isNotBlank()) token
//            else null
//        }
}
