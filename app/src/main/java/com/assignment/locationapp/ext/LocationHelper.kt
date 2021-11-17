package com.assignment.locationapp.ext

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class UserLocation {
    //call this class and its function on onResume for permissions
    var dialog: Dialog? = null
    private var fusedLocationClient: FusedLocationProviderClient? = null

    var gpsLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun checkPermissions(context: Context, onLocationReceived: (location: Location) -> Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            //checks for permissions and enters if all good
            //initiate location manager
            val locationManager: LocationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGPSEnabled: Boolean =
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//            Log.d("GPS","$isGPSEnabled")
            if (isGPSEnabled) {
                gpsLiveData.value = false
                //if gps in on gets user location using fused location client
                val criteria = Criteria()
                criteria.accuracy = Criteria.ACCURACY_FINE
                criteria.horizontalAccuracy = Criteria.ACCURACY_HIGH
                criteria.bearingAccuracy = Criteria.ACCURACY_HIGH
                criteria.isBearingRequired = true

                fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
                fusedLocationClient?.lastLocation?.addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        onLocationReceived(location)
                    } else {
                        //another way to get location
                        if (location?.latitude == null) {
                            locationManager.requestSingleUpdate(criteria, object :
                                LocationListener {
                                override fun onLocationChanged(locations: Location) {
                                    onLocationReceived(locations)
                                }

                                override fun onStatusChanged(
                                    provider: String,
                                    status: Int,
                                    extras: Bundle
                                ) {

                                }

                                override fun onProviderEnabled(provider: String) {
                                }

                                override fun onProviderDisabled(provider: String) {
                                }
                            }, null)
                        }
                    }

                }
                //dialog may be null but if dialog isShowing will always return
                val dialogFlag: Boolean = dialog?.isShowing ?: false
                if (dialogFlag) {
                    dialog?.dismiss()
                }
            } else {
                //show dialog telling to turn on gps
            }
        }
    }


}
