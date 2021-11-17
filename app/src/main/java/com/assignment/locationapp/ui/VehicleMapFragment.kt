package com.assignment.locationapp.ui

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.assignment.locationapp.R
import com.assignment.locationapp.data.model.PoiModel
import com.assignment.locationapp.data.model.VehicleResponse
import com.assignment.locationapp.databinding.FragmentMapVehicleBinding
import com.assignment.locationapp.ext.Constants
import com.assignment.locationapp.ext.Resource
import com.assignment.locationapp.ext.UserLocation
import com.assignment.locationapp.ext.formatCoordinate
import com.assignment.locationapp.ui.viewmodel.VehicleViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class VehicleMapFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapVehicleBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //set lat and long within a bound given
    private var latitude: Double = Constants.STARTING_LAT
    private var longitude: Double = Constants.STARTING_LONG
    private var map: GoogleMap? = null
    private lateinit var googleMapsFragment: SupportMapFragment
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val latLongList = arrayListOf<LatLng>()
    private var userLocation: UserLocation? = null
    private var moveMap: Boolean = true

    private val vehicleViewModel by viewModels<VehicleViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMapVehicleBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkLocation()
        getVehicleData()
        initMap()
    }

    private fun checkLocation() {
        //fetch user location for the first time
        userLocation = UserLocation()
        userLocation?.checkPermissions(requireContext()) {

        }
    }


    private fun initMap() {
        googleMapsFragment = childFragmentManager.findFragmentById(R.id.map)
                as SupportMapFragment
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        googleMapsFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap!!
        map?.uiSettings?.isMapToolbarEnabled = false
        map?.setOnCameraIdleListener {
            latitude = map?.cameraPosition?.target!!.latitude
            longitude = map?.cameraPosition?.target!!.longitude
        }
    }

    private fun getVehicleData() {
        vehicleViewModel.loadVehicleData().observe(viewLifecycleOwner, {
            it?.let { resource ->
                handleVehicleData(resource)
            }
        })
    }

    private fun handleVehicleData(resource: Resource<VehicleResponse>) {
        when (resource.status) {
            Resource.Status.SUCCESS -> {
                resource.data?.poiList?.let { vehicleList ->
                    latLongList.clear()
                    storeLatLong(vehicleList)
                }
            }
            Resource.Status.ERROR, Resource.Status.NO_INTERNET -> {
                resource.message?.let {
                    Toast.makeText(
                        requireContext(),
                        it,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            Resource.Status.LOADING -> {
            }
        }
    }

    private fun storeLatLong(data: List<PoiModel>) {
        for (i in data) {
            i.coordinateData?.latitude?.let { lat ->
                i.coordinateData.longitude?.let { long ->
                    latLongList.add(LatLng(lat, long))
                }
            }
        }
        addMarkersToMap(latLongList, data)
    }

    private fun addMarkersToMap(latLng: List<LatLng>, data: List<PoiModel>) {
        map?.clear()
        //default value to store markers
        val mMarkersList: MutableList<Marker> = ArrayList()
        for (latLngValue in latLng) {
            //iterate through lat longs
            val marker: Marker? = map?.addMarker(
                MarkerOptions()
                    .position(latLngValue)
                    .icon(drawMarker(R.drawable.ic_marker))
            )
            if (marker != null) {
                // add marker to array list
                mMarkersList.add(marker)
            }
        }

        for (i in latLng.indices) {
            val options = MarkerOptions()
            options.icon(drawMarker(R.drawable.ic_marker))
            map?.addMarker(options.position(latLng[i]))
            map?.setOnMarkerClickListener { p0 ->
                p0?.position?.let {

                }
                Timber.d("value= %s", p0?.position?.latitude)
                false
            }

            // zoom our camera on map.
            map?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng[0], 12f))
            // Setting a custom info window adapter for the google map
            moveMap = false
        }
        map?.setOnMarkerClickListener { p0 ->
            //reset initially changed marker back to original icon
            for (otherMarker in mMarkersList) {
                otherMarker.setIcon(drawMarker(R.drawable.ic_marker))
            }
            map?.moveCamera(CameraUpdateFactory.newLatLngZoom(p0.position, 15f))
            //change the active/recently clicked marker to black color
            p0?.setIcon(drawMarker(R.drawable.ic_marker_black))
            p0?.position?.let {
                showToast(it, data)
            }
            Timber.d("value= %s", p0?.position?.latitude)
            false
        }
    }

    //set custom marker
    private fun drawMarker(drawableValue: Int): BitmapDescriptor {
        val circleDrawable = ContextCompat.getDrawable(
            requireContext(),
            drawableValue
        )
        return getMarkerIconFromDrawable(circleDrawable!!)
    }

    private fun getMarkerIconFromDrawable(drawable: Drawable): BitmapDescriptor {
        val canvas = Canvas()
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        canvas.setBitmap(bitmap)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    //show message to notify a vehicle we are trying to call with its data
    private fun showToast(position: LatLng, data: List<PoiModel>) {
        //define iteration
        var i: Int
        val dataList: ArrayList<PoiModel> =
            data as ArrayList<PoiModel>
        i = 0
        var count = 0
        if (i > data.size) {
            dataList.clear()
        }
        //iteration to loop through list of lat longs a marker is defined in a map
        for (j in dataList.iterator()) {
            val passedLatLng = LatLng(j.coordinateData?.latitude!!, j.coordinateData.longitude!!)
            if (position == passedLatLng) {
                i = count
                break
            } else {
                count++
            }
        }
        Toast.makeText(
            requireContext(),
            "Vehicle with id"
                    + data[i].vehicleId
                    + "is heading from Latitude= "
                    + formatCoordinate(data[i].coordinateData?.latitude.toString())
                    + " and longitude= "
                    + formatCoordinate(data[i].coordinateData?.longitude.toString()),
            Toast.LENGTH_LONG
        ).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}