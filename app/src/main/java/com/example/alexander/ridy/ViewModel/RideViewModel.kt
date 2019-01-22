package com.example.alexander.ridy.ViewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.alexander.ridy.Model.domain.Ride
import com.example.alexander.ridy.Model.domain.VehicleType
import com.example.alexander.ridy.Model.network.InjectedViewModel
import com.example.alexander.ridy.Model.persistence.App
import com.example.alexander.ridy.Model.persistence.Ride.RideRepository
import javax.inject.Inject

/**
 * Class that represents a model that can have information of a Ride Object
 * @property rideRepository an abstraction layer that connects to the DAO for retrieving data out of the local database
 * @property allRides a list of all the rides from the local database
 * @property distanceRide distance of the ride
 * @property vehicleType the vehicle used in the ride
 * @property moneySaved the money saved in the ride
 * @property rideToWork determines if it's a ride to work or not
 */
class RideViewModel : InjectedViewModel() {


    @Inject
    lateinit var rideRepository: RideRepository

    init {
        App.component.inject(this)
    }

    val allRides : LiveData<List<Ride>> = rideRepository.rides

    private var distanceRide = MutableLiveData<Double>()
    private var vehicleType = MutableLiveData<VehicleType>()
    private var moneySaved = MutableLiveData<Double>()
    private var rideToWork = MutableLiveData<Boolean>()

    /**
     * Function that inserts a ride into the local database
     * @param ride represents a Ride object
     */
    fun insert(ride : Ride) {
        rideRepository.insert(ride)
    }

    /**
     * Function that deletes a ride into the local database
     * @param ride represents a Ride object
     */
    fun delete(ride : Ride) {
        rideRepository.delete(ride)
    }

    /**
     * Function that deletes all ride from the local database
     */
    fun deleteAll() {
        rideRepository.deleteAll()
    }

    /**
     * Function that sets the local distanceRide variable
     * @param distanceRide a certain distance
     */
    fun setDistanceRide(distanceRide : Double) {
        this.distanceRide.value = distanceRide
    }

    /**
     * Function that sets the local vehicleType variable
     * @param vehicleType a certain vehicleType
     */
    fun setVehicleType(vehicleType : VehicleType) {
        this.vehicleType.value = vehicleType
    }

    /**
     * Function that sets the local rideToWork variable
     * @param rideToWork a variable which defines if it's a ride to work or not
     */
    fun setRideToWork(rideToWork: Boolean) {
        this.rideToWork.value = rideToWork
    }

    /**
     * Function that gets the local distanceRide variable
     * @return returns a certain distance
     */
    fun getDistanceRide(): MutableLiveData<Double> {
        return distanceRide
    }

    /**
     * Function that gets the local vehicleType variable
     * @return returns a certain vehicleType
     */
    fun getVehicleType(): MutableLiveData<VehicleType> {
        return vehicleType
    }

    /**
     * Function that gets the local moneySaved variable
     * @return returns a certain value of money that has been saved
     */
    fun getMoneySaved(): MutableLiveData<Double> {
        return moneySaved
    }

    /**
     * Function that gets the local rideToWork variable
     * @return returns a yes/no depending on the ride
     */
    fun getRideToWork(): MutableLiveData<Boolean> {
        return rideToWork
    }

    /**
     * Function that calculates how much money that has been saved in a ride
     * @param distance the distance of the ride
     * @param refundTravelExpensesPerKm how much euro you get per KM
     * @param fuelUsagePerKm how much fuel your car consumes /100KM
     * @param fuelPriceSpecificCar fuel price for your specific car
     */
    fun calculateMoneySaved(distance : Double, refundTravelExpensesPerKm : Double, fuelUsagePerKm : Double, fuelPriceSpecificCar : Double) {
        var moneySavedNoCar = 0.0 //init //naamgeving: hij houdt enkel rekening met naft bij gebruik fiets

        if (distance == 0.0) {
            this.moneySaved.value = 0.0
        } else {
            moneySavedNoCar = ((fuelUsagePerKm / 100) * (distance / 1000)) * fuelPriceSpecificCar //regel van drie
        }

        if (rideToWork.value!!) {
            //naar werk met auto
            if (vehicleType.value == VehicleType.CAR) {
                this.moneySaved.value =  (distance/1000) * refundTravelExpensesPerKm
            } else if (vehicleType.value == VehicleType.BIKE){
                //naar werk met fiets
                this.moneySaved.value =  (distance/1000) * refundTravelExpensesPerKm + moneySavedNoCar
            }
        }  else {
            //niet naar werk, voertuig auto
            if (vehicleType.value == VehicleType.CAR) {
                this.moneySaved.value = 0.0
            } else if (vehicleType.value == VehicleType.BIKE){
                //niet naar werk, voertuig fiets
                this.moneySaved.value = moneySavedNoCar
            }
        }
    }
}