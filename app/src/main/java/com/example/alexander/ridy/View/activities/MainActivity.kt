package com.example.alexander.ridy

import android.arch.lifecycle.ViewModelProviders
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast
import com.example.alexander.ridy.View.fragments.Journal.Journal
import com.example.alexander.ridy.View.fragments.More.More
import com.example.alexander.ridy.fragments.Ride.SelectVehicle
import kotlinx.android.synthetic.main.activity_main.*
import com.example.alexander.ridy.View.fragments.Ride.ExecuteRide
import com.example.alexander.ridy.View.fragments.Ride.VehicleDetails
import com.example.alexander.ridy.ViewModel.RideViewModel
import kotlinx.android.synthetic.main.toolbar.*

/**
 * The main activity. This activity takes care of the navigation and the dynamic fragments
 * @property selectVehicle variable which represents a selectVehicle fragment
 * @property vehicleDetails variable which represents a vehicleDetails fragment
 * @property ride variable which represents a ExecuteRide fragment
 * @property rideViewModel variable which represents a rideViewModel
 */
class MainActivity : AppCompatActivity(), SelectVehicle.ToVehicleScreenCallBack, VehicleDetails.ToRideScreenCallBack, ExecuteRide.Companion.ToSelectScreenCallBack {

    lateinit var selectVehicle: SelectVehicle
    lateinit var vehicleDetails: VehicleDetails
    lateinit var ride: ExecuteRide
    private lateinit var rideViewModel: RideViewModel

    /**
     * This method is used to store and restore information about the state of your activity.
     * In cases such as orientation changes, closing the app or any other scenario that leads to onCreate() being recalled,
     * @param savedInstanceState the savedInstanceState bundle can be used to reload the previous state information.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        toolbarText.text="Select Vehicle"
        rideViewModel = ViewModelProviders.of(this).get(RideViewModel::class.java)
        deleteAllRides.setOnClickListener() {
            AsyncTask.execute {
                rideViewModel.deleteAll()
            }
        }
        //first screen when app launches
        val selectVehicle = SelectVehicle.newInstance()
        this.selectVehicle = selectVehicle
        setFragment(selectVehicle, R.id.fragment_container)
    }

    /**
     * Constant that is an instance of a bottomNavigationViewItemListener which will take care of the navigation through the application(bottom)
     */
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.nav_ride -> {
                toolbarText.text="Select Vehicle"
                deleteAllRides.visibility = View.INVISIBLE
                val selectVehicle = SelectVehicle.newInstance()
                setFragment(selectVehicle, R.id.fragment_container)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_journal -> {
                toolbarText.text="Journal"
                deleteAllRides.visibility = View.VISIBLE
                val journal = Journal.newInstance()
                setFragment(journal, R.id.fragment_container)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_more -> {
                toolbarText.text="More"
                deleteAllRides.visibility = View.INVISIBLE
                val more = More.newInstance()
                setFragment(more, R.id.fragment_container)
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }

    /**
     * Callback to go the the ride screen
     */
    override fun goToRideScreen() {
        fixScreen(vehicleDetails,ExecuteRide.newInstance(),"ride","Ride")
    }

    /**
     * Callback to go the the vehicleDetails screen
     */
    override fun goToVehicleScreen() {
        fixScreen(selectVehicle ,VehicleDetails.newInstance(),"vehicleDetails","Vehicle Details")
    }

    /**
     * Callback to go the the select vehicle screen
     */
    override fun goToSelectScreen() {
        fixScreen(ride ,SelectVehicle.newInstance(),"selectVehicle","Select Vehicle")
    }

    /**
     * Function to switch the screen -> fragment
     * @param fragmentRemove the fragment that has to be removed from the activity
     * @param fragmentNew the fragment that has to be added to the activity
     * @param locVarFrag a string that tells the function which fragment to display
     * @param textToolbar represents the text in the toolbar
     */
    fun fixScreen(fragmentRemove: Fragment, fragmentNew: Fragment, locVarFrag : String, textToolbar : String) {
        removeFragment(fragmentRemove)
        val fragmentNew = fragmentNew
        when (locVarFrag) {
            "selectVehicle" -> this.selectVehicle = fragmentNew as SelectVehicle
            "vehicleDetails" -> this.vehicleDetails = fragmentNew as VehicleDetails
            "ride" -> this.ride = fragmentNew as ExecuteRide
        }
        setFragment(fragmentNew, R.id.fragment_container)
        if (textToolbar != "") {
            toolbarText.text = textToolbar
        }
    }

    /**
     * Method to dynamically set the fragment in an activity
     * @param fragment the fragment that has to be set
     * @param int where in the UI the fragment has to be placed
     */
    fun setFragment(fragment: Fragment, int: Int) {
        var fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(int, fragment)
        fragmentTransaction.commit()
    }

    /**
     * Method to dynamically remove the fragment from an activity
     * @param fragment the fragment that has to be removed
     */
    fun removeFragment(fragment: Fragment) {
        var fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.remove(fragment)
        fragmentTransaction.commit()
    }
}
