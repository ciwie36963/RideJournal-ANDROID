package com.example.alexander.ridy.View.fragments.Ride

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.alexander.ridy.Model.extensions.InputRegex
import com.example.alexander.ridy.R
import kotlinx.android.synthetic.main.fragment_vehicledetails.*
import android.widget.TextView
import android.widget.Spinner
import android.widget.Toast
import com.example.alexander.ridy.MainActivity
import com.example.alexander.ridy.Model.domain.Gasoline
import com.example.alexander.ridy.Model.domain.GasolineType
import com.example.alexander.ridy.Model.domain.Ride
import com.example.alexander.ridy.Model.extensions.ConnectivityManager
import com.example.alexander.ridy.Model.network.Endpoint
import com.example.alexander.ridy.Model.network.RetrofitClientInstance
import com.example.alexander.ridy.ViewModel.GasolineViewModel
import com.example.alexander.ridy.ViewModel.RideViewModel
import com.example.alexander.ridy.ViewModel.VehicleViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors

/**
 * A Fragment is a section of an Activity, which has its own lifecycle, receives its own input events, can be added or removed while the Activity is running.
 * This fragment takes care of representing the journal containing the rides
 * @property parent variable that makes it able to call the function in the ToRideScreenCallBack interface
 * @property rideViewModel a model which holds temporary data of a ride
 * @property vehicleViewModel a model which holds temporary data of a vehicle
 * @property gasolineViewModel a model which holds temporary data of gasoline
 * @property currentGasoline variable that represents current gasoline
 * @property gasolineType variable that represents a gasoline type
 * @property internetConnectivity variable that determines if there is wifi/4g or not
 */
class VehicleDetails : Fragment() {

    lateinit var parent: ToRideScreenCallBack
    private lateinit var vehicleViewModel: VehicleViewModel
    private lateinit var gasolineViewModel: GasolineViewModel
    private lateinit var rideViewModel: RideViewModel
    private lateinit var currentGasoline: Gasoline
    lateinit var gasolineType: GasolineType
    var internetConnectivity : Boolean = false

    /**
     * This method is used to store and restore information about the state of your activity.
     * In cases such as orientation changes, closing the app or any other scenario that leads to onCreate() being recalled,
     * @param savedInstanceState the savedInstanceState bundle can be used to reload the previous state information.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parent = (activity as ToRideScreenCallBack)
        vehicleViewModel = ViewModelProviders.of(activity!!).get(VehicleViewModel::class.java)
        gasolineViewModel = ViewModelProviders.of(activity!!).get(GasolineViewModel::class.java)
        rideViewModel = ViewModelProviders.of(activity!!).get(RideViewModel::class.java)
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
        val view = inflater.inflate(R.layout.fragment_vehicledetails, container, false)
        return view
    }

    /**
     * If the app continues after the onPause method is called, the onResume method is called.
     * It is one of the methods of the activity life cycle.
     * The function takes care of the internet check [internetConnectivity] and will check the workSwitch and btnSave for eventual changes
     */
    override fun onResume() {
        super.onResume()

        ConnectivityManager(object : ConnectivityManager.Consumer {
            override fun accept(internet: Boolean?) {
                internetConnectivity = internet!!
            }
        })

        workSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                travelAllowanceEditText.isEnabled = false
                travelAllowanceEditText.inputType = InputType.TYPE_NULL
                travelAllowanceEditText.setText("", TextView.BufferType.EDITABLE);
            } else {
                travelAllowanceEditText.isEnabled = true
                travelAllowanceEditText.inputType = InputType.TYPE_CLASS_TEXT
            }
        }

        btnSave.setOnClickListener() {
            saveVehicle()
        }
    }

    /**
     * Function that uses the regex in the extensions directory to determine if the value inputted is OK
     * @return returns true/false whether check is ok or not
     */
    fun checkFuelUsagePer100kmEditText(): Boolean {
        if (InputRegex.controleerVeld(fuelUsagePerKmEditText.text.toString())) {
            return true
        } else {
            fuelUsagePerKmEditText.setError("Foutieve ingave")
        }
        return false
    }

    /**
     * Function that uses the regex in the extensions directory to determine if the value inputted is OK
     * @return returns true/false whether check is ok or not
     */
    fun checkTravelAllowanceEditText(): Boolean {
        if (workSwitch.isChecked) {
            if (InputRegex.controleerVeld(travelAllowanceEditText.text.toString())) {
                return true
            } else {
                travelAllowanceEditText.setError("Foutieve ingave")
            }
        } else {
            return true
        }
        return false
    }

    /**
     * Function that determines the gasolinetype and afterwards
     *              - Retrieves the most actual price of the gasoline used online, deletes the previous records and puts the most recent value in the database
     *              - Retrieves the data from the local database in case the internet on the devices is not working
     *              - Uses default values for the gasolines in case there is no internet and no data in the local database
     */
    fun determineGasolineType() {
        val mySpinner = view?.findViewById(R.id.gasolineSpinner) as Spinner
        val text = mySpinner.selectedItem.toString()

        when (text) {
            "Super95" -> this.gasolineType = GasolineType.Super95
            "LPG" -> this.gasolineType = GasolineType.LPG
            "Diesel" -> this.gasolineType = GasolineType.Diesel
        }

        if (internetConnectivity) {
            //ER IS INTERNET dus data van internet halen
            gasolineViewModel.getGasoline(gasolineType)
            gasolineViewModel.getCurrentGasoline().observe(activity!!, Observer<Gasoline> { _ ->
                currentGasoline = gasolineViewModel.getCurrentGasoline().value!!
                vehicleViewModel.setGasoline(currentGasoline)
                //ER IS INTERNET DUS DATA DELETEN + INSERTEN IN LOKALE DB(updaten niet gedaan vanwege langer stuk schrijven -> controle indien de brandstof er al is etc
                AsyncTask.execute {
                    gasolineViewModel.deleteAllGasolines()
                    gasolineViewModel.insert(currentGasoline)
                }
            })
        } else {
            gasolineViewModel.allGasolines.observe(activity!!, Observer<List<Gasoline>> { _ ->
                if (!gasolineViewModel.allGasolines.value!!.isEmpty()) {
                    gasolineViewModel.allGasolines.value!!.forEach { gas ->
                        if (gasolineType == gas.gasolineType) {
                            //geen internet maar wel data uit db
                            vehicleViewModel.setGasoline(gas)
                        } else {
                        //geen internet en db leeg dus standaardwaarden uit db halen"
                        useDefaultValues(gasolineType)
                    } } }
        })

        //-----------------Retrieving prices without using dependency injection dagger2(Change Observable to Call in Endpoint, comment out GetGasoline(x) and onCleared -> subscription in GasVM)
/*        val service = RetrofitClientInstance().getRetrofitInstance()!!.create(Endpoint::class.java!!)
        lateinit var call : Call<Gasoline>
        when(text) {
            "Super95" -> call = service.getSuper95()
            "LPG" -> call = service.getLPG()
            "Diesel" -> call = service.getDiesel()
        }
        call.enqueue(object : Callback<Gasoline> {
            override fun onFailure(call: Call<Gasoline>, t: Throwable) {
                Log.d("Error", t.message)
            }

            override fun onResponse(call: Call<Gasoline>, response: Response<Gasoline>) {
                var gas = response.body()
            }
        })*/
    }
    }

    /**
     * Function that puts fixed values in the vehicleViewModel in case there is no internet or data in the database
     * @param gasolineType type of the gasoline
     */
    fun useDefaultValues(gasolineType: GasolineType) {
        when (gasolineType) {
            GasolineType.Diesel -> vehicleViewModel.setGasoline(Gasoline(1.5, gasolineType))
            GasolineType.Super95 -> vehicleViewModel.setGasoline(Gasoline(1.45, gasolineType))
            GasolineType.LPG -> vehicleViewModel.setGasoline(Gasoline(0.51, gasolineType))
        }
    }

    /**
     * Function that checks the input textfields and saves the settings temporary in a viewModel
     */
    fun saveVehicle() {
        if (checkFuelUsagePer100kmEditText() && checkTravelAllowanceEditText()) {
            rideViewModel.setRideToWork(workSwitch.isChecked)
            vehicleViewModel.setFuelUsagePerKm(fuelUsagePerKmEditText.text.toString().toDouble())
            if (travelAllowanceEditText.text.toString() == "") {
                vehicleViewModel.setRefundTravelExpensesPerKm(0.0)
            } else {
                vehicleViewModel.setRefundTravelExpensesPerKm(travelAllowanceEditText.text.toString().toDouble())
            }
            determineGasolineType()
            parent.goToRideScreen()
        }
    }

    /**
     * Interface that will be used between activity/fragment to go to the ride screen
     */
    interface ToRideScreenCallBack {
        fun goToRideScreen()
    }

    /**
     * This object is a singleton onject that has to be called by its named. Every method can be used in other classes
     */
    companion object {

        /**
         * Function returns an instance of a SelectVehicle fragment
         * @return a selectVehicle instance
         */
        fun newInstance(): VehicleDetails {
            return VehicleDetails()
        }
    }
}