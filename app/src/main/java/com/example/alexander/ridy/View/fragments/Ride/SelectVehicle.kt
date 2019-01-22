package com.example.alexander.ridy.fragments.Ride

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.alexander.ridy.Model.domain.VehicleType
import com.example.alexander.ridy.R
import com.example.alexander.ridy.ViewModel.VehicleViewModel
import kotlinx.android.synthetic.main.fragment_selectvehicle.*

/**
 * A Fragment is a section of an Activity, which has its own lifecycle, receives its own input events, can be added or removed while the Activity is running.
 * This fragment takes care of representing the screen where you can select a vehicle
 * @property parent variable that makes it able to call the function in the ToVehicleScreenCallBack interface
 * @property vehicleViewModel a model that temporary holds data of a vehicle
 */
class SelectVehicle : Fragment() {

    lateinit var parent: ToVehicleScreenCallBack
    private lateinit var vehicleViewModel: VehicleViewModel

    /**
     * This method is used to store and restore information about the state of your activity.
     * In cases such as orientation changes, closing the app or any other scenario that leads to onCreate() being recalled,
     * @param savedInstanceState the savedInstanceState bundle can be used to reload the previous state information.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parent = (activity as ToVehicleScreenCallBack)
        vehicleViewModel = ViewModelProviders.of(activity!!).get(VehicleViewModel::class.java)
    }

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
        val view = inflater.inflate(R.layout.fragment_selectvehicle, container, false)
        return view
    }

    /**
     * If the app continues after the onPause method is called, the onResume method is called.
     * It is one of the methods of the activity life cycle.
     * The function makes sure you can go to the Car/Bike screen by pressing a button
     */
    override fun onResume() {
        super.onResume()

        btnCar.setOnClickListener {
            vehicleViewModel.setVehicleType(VehicleType.CAR)
            parent.goToVehicleScreen()
        }
        btnBike.setOnClickListener {
            vehicleViewModel.setVehicleType(VehicleType.BIKE)
            parent.goToVehicleScreen()
        }
    }

    /**
     * Interface that will be used between activity/fragment to go to the vehicle details screen
     */
    interface ToVehicleScreenCallBack {
        /**
         * Function used to go the the vehicle details screen
         */
        fun goToVehicleScreen()
    }

    /**
     * This object is a singleton onject that has to be called by its named. Every method can be used in other classes
     */
    companion object {

        /**
         * Function returns an instance of a SelectVehicle fragment
         * @return a selectVehicle instance
         */
        fun newInstance(): SelectVehicle {
            return SelectVehicle()
        }
    }
}