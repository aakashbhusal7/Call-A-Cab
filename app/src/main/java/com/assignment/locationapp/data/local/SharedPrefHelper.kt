package com.assignment.locationapp.data.local

import com.f2prateek.rx.preferences2.RxSharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefHelper @Inject constructor(
    private val sharedPreferences: RxSharedPreferences
) {

    //api token saved to shared prefs, optional use case
   // val apiToken by lazy { sharedPreferences.getString(API_KEY, BuildConfig.API_TOKEN) }

    companion object {
        private const val API_KEY = "SharedPreferencesHelper:apiToken"
    }
}
