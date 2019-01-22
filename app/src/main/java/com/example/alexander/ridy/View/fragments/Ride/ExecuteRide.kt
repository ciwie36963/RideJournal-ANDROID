package com.example.alexander.ridy.View.fragments.Ride

import android.arch.lifecycle.Observer
import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.alexander.ridy.R
import com.example.alexander.ridy.ViewModel.RideViewModel
import com.example.alexander.ridy.ViewModel.VehicleViewModel
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import kotlinx.android.synthetic.main.fragment_ride.*
import android.widget.Toast
import com.example.alexander.ridy.MainActivity
import android.content.Intent
import android.net.Uri
import com.example.alexander.ridy.Model.domain.Ride
import android.os.AsyncTask
import com.example.alexander.ridy.R.color.white

/**
 * A Fragment is a section of an Activity, which has its own lifecycle, receives its own input events, can be added or removed while the Activity is running.
 * This fragment takes care of representing the journal containing the rides
 * @property parent variable that makes it able to call the function in the ToSelectScreenCallBack interface
 * @property rideViewModel a model which holds temporary data of a ride
 * @property vehicleViewModel a model which holds temporary data of a vehicle
 * @property mMap a mapView which will show the users where he is
 * @property map a map that will be used to for retrieving the location of the user
 * @property fusedLocationClient a location client which will be needed to request location updates and will take of a loop which will determine the location of the user
 * @property lastLocation the last location of the user
 * @property locationCallback object needed for retrieving the location every 5 sec
 * @property locationRequest a request to retrieve the location from the user to the system
 * @property ride a variable that represents a ride object
 * @property location a variable that represents a location
 * @property distance a variable that represents a certain distance
 *
 */
class ExecuteRide : Fragment(), OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {

    private var mMap: MapView? = null
    lateinit var map : GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    lateinit var ride : Ride
    private var location: Location? = null
    private var distance : Double = 0.0
    private lateinit var rideViewModel: RideViewModel
    private lateinit var vehicleViewModel : VehicleViewModel
    lateinit var parent: ToSelectScreenCallBack

    /**
     * onCreateView is called to "inflate" the layout of the fragment,
     * i.e. graphic initialization is usually done here.
     * It is always called after the onCreate method.
     * @param inflater the attr that handles the inflation of the fragment
     * @param container attr that forms a container for the data to be displayed
     * @param savedInstanceState the savedInstanceState bundle can be used to reload the previous state information.
     * @return function returns a view, this will be displayed on the screen
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_ride, container, false)
        mMap = view?.findViewById(R.id.mapView) as MapView
        mMap?.onCreate(savedInstanceState)
        mMap?.getMapAsync(this)
        return view
    }

    /**
     * Function that asks permission from the user to determine his location
     * In case it is OK, the location services will be enabled
     */
    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(activity!!.applicationContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity!!, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        map.setMyLocationEnabled(true)
    }

    /**
     * Function that asks permission from the user to determine his location
     * In case it is OK, the [fusedLocationClient] will start making requests for the location
     */
    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(activity!!.applicationContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity!!, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null /* Looper */)
    }

    /**
     * Function that creates a location request with a certain interval and a certain accuracy
     * Will alsk to enable google map services in case it isn't activated
     */
    @SuppressLint("MissingPermission")
    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client = LocationServices.getSettingsClient(activity!!.applicationContext)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            startLocationUpdates()
        }
        task.addOnFailureListener { e ->
            if (e is ResolvableApiException) {
                try {
                   e.startResolutionForResult(activity,
                        REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                }
            }
        }
    }

    /**
     * Function that will automatically be called when the google map is ready and will call the setUpMap and setLocation function
     * @param map a google map
     */
    @SuppressLint("MissingPermission")
    override fun onMapReady(map: GoogleMap) {
        this.map = map;
        setUpMap()
        setLocation()
    }

    /**
     * Function that will adjust the camera of the screen to your exact position when it is able to retrieve the last location
     */
    @SuppressLint("MissingPermission")
    fun setLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener(activity!!) { location ->
            // Got last known location. In some rare situations this can be null
            map.setMyLocationEnabled(true)
            if (location != null) {
                map.setMyLocationEnabled(true)
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }
    }

    /**
     * This method is used to store and restore information about the state of your activity.
     * In cases such as orientation changes, closing the app or any other scenario that leads to onCreate() being recalled
     * This method has the LocationCallback
     * @param savedInstanceState the savedInstanceState bundle can be used to reload the previous state information.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parent = (activity as ToSelectScreenCallBack)
        rideViewModel = ViewModelProviders.of(activity!!).get(RideViewModel::class.java)
        vehicleViewModel = ViewModelProviders.of(activity!!).get(VehicleViewModel::class.java)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)

        locationCallback = object : LocationCallback() {
            /**
             * Function that will automaticallyy be called and will get the latest location of the user
             * The function will also calculate the distance between the locations
             */
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                if (location == null) {
                    location = p0.lastLocation
                } else {
                    distance += location!!.distanceTo(p0.lastLocation).toDouble()
                    location = p0.lastLocation
                }
            }
        }
    }

    /**
     * If the app continues after the onPause method is called, the onResume method is called.
     * It is one of the methods of the activity life cycle.
     * It takes care of the action that has to take place when pressed on btnStart
     * It takes care of the action that has to take place when pressed on btnStop
     */
    override fun onResume() {
        super.onResume()
        mMap?.onResume()

        btnStart.setOnClickListener() {
            createLocationRequest()
            setLocation()
            btnStop.isEnabled = true
            btnStop.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        }

        btnStop.setOnClickListener() {
            fusedLocationClient.removeLocationUpdates(locationCallback)

            //save the ride
            //attr1 - distance
            rideViewModel.setDistanceRide(distance)
            //attr2 - vehicleType
            vehicleViewModel.getVehicleType().observe(activity!!, Observer{ _ ->
                rideViewModel.setVehicleType(vehicleViewModel.getVehicleType().value!!)
            })
            //attr4 - rideToWork, reeds gedaan in selectVehicle frag
            //attr3
            rideViewModel.calculateMoneySaved(rideViewModel.getDistanceRide().value!!, vehicleViewModel.getRefundTravelExpensesPerKm().value!!, vehicleViewModel.getFuelUsagePerKm().value!!, vehicleViewModel.getGasoline().value!!.price)

            //create ride
            ride = Ride(rideViewModel.getDistanceRide().value!!,rideViewModel.getVehicleType().value!!, rideViewModel.getMoneySaved().value!!, rideViewModel.getRideToWork().value!!)

            //insert ride
            AsyncTask.execute {
                rideViewModel.insert(ride)
            }
            //next screen
            Toast.makeText(activity!!.applicationContext, "Ride Saved!", Toast.LENGTH_SHORT).show()
            parent.goToSelectScreen()
        }
    }

    /**
     * Invoked when the activity may be temporarily destroyed, save the instance state here
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mMap?.onSaveInstanceState(outState)
    }

    /**
     * The system calls this method as the first indication that the user is leaving
     * your activity (though it does not always mean the activity is being destroyed);
     * It indicates that the activity is no longer in the foreground (though it may still be visible if the user is in multi-window mode).
     * The [mMap] than has to stop and the [fusedLocationClient] also
     */
    override fun onPause() {
        super.onPause()
        mMap?.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    /**
     * When the activity enters the Started state, the system invokes this callback. The onStart() call makes the activity visible to the user,
     * as the app prepares for the activity to enter the foreground and become interactive.
     * The [mMap] has to be started
     */
    override fun onStart() {
        super.onStart()
        mMap?.onStart()
    }

    /**
     * When your activity is no longer visible to the user, it has entered the Stopped state, and the system invokes the onStop() callback.
     * This may occur, for example, when a newly launched activitycovers the entire screen. The system may also call onStop() when the
     * activity has finished running, and is about to be terminated.
     * The [mMap] has to be stopped
     */
    override fun onStop() {
        super.onStop()
        mMap?.onStop()
    }

    /**
     * onDestroy() is called before the activity is destroyed. The system invokes this callback either because:
     * -the activity is finishing (due to the user completely dismissing the activity or due to finish() being called on the activity), or
     * -the system is temporarily destroying the activity due to a configuration change (such as device rotation or multi-window mode)
     * The [mMap] has to be destroyed
     */
    override fun onDestroy() {
        super.onDestroy()
        mMap?.onDestroy()
    }

    /**
     * When the phone's memory is low, the background processes will be killed by framework.
     * If the last background process is killed, the framework will call onLowMemory of every app.
     */
    override fun onLowMemory() {
        super.onLowMemory()
        mMap?.onLowMemory()
    }

    /**
     * This object is a singleton onject that has to be called by its named. Every method can be used in other classes
     * @property LOCATION_PERMISSION_REQUEST_CODE location permission request code
     * @property REQUEST_CHECK_SETTINGS request check settings code
     */
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val REQUEST_CHECK_SETTINGS = 2

        /**
         * Function used to go the the select vehicle screen
         */
        interface ToSelectScreenCallBack {
            fun goToSelectScreen()
        }

        /**
         * Function returns an instance of a Execute Ride fragment
         * @return an ExecuteRide instance
         */
        fun newInstance(): ExecuteRide {
            return ExecuteRide()
        }

    }
}