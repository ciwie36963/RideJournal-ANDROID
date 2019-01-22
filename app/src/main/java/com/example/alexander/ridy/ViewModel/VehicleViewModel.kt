package com.example.alexander.ridy.ViewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.alexander.ridy.Model.domain.Gasoline
import com.example.alexander.ridy.Model.domain.VehicleType

/**
 * Class that represents a model that can have information of a Vehicle Object
 * @property refundTravelExpensesPerKm how much euro you get per KM
 * @property fuelUsagePerKm how much fuel your car consumes /100KM
 * @property gasoline a certain gasoline object
 * @property vehicleType the vehicletype -> car/bike
 */
class VehicleViewModel : ViewModel() {

    private var refundTravelExpensesPerKm = MutableLiveData<Double>()
    private var fuelUsagePerKm = MutableLiveData<Double>()
    private var gasoline = MutableLiveData<Gasoline>()
    private var vehicleType = MutableLiveData<VehicleType>()

    /**
     * Function that sets the local refundTravelExpensesPerKm variable
     * @param travelExpenses the money you retrieve from your work by going to work in a certain vehicle
     */
    fun setRefundTravelExpensesPerKm(travelExpenses : Double) {
        this.refundTravelExpensesPerKm.value = travelExpenses
    }

    /**
     * Function that sets the local fuelUsagePerKm variable
     * @param fuelUsagePerKm how much fuel your car consumes /100KM
     */
    fun setFuelUsagePerKm(fuelUsagePerKm : Double) {
        this.fuelUsagePerKm.value = fuelUsagePerKm
    }

    /**
     * Function that sets the local gasoline variable
     * @param gasoline a certain gasoline object
     */
    fun setGasoline(gasoline: Gasoline) {
        this.gasoline.value = gasoline
    }

    /**
     * Function that sets the local vehicleType variable
     * @param vehicleType a certain vehicleType
     */
    fun setVehicleType(vehicleType : VehicleType) {
        this.vehicleType.value = vehicleType
    }

    /**
     * Function that gets the local VehicleType variable
     * @return returns a certain vehicletype
     */
    fun getVehicleType(): MutableLiveData<VehicleType> {
        return vehicleType
    }

    /**
     * Function that gets the local fuelUsagePerKm variable
     * @return returns a certain fuelUsagePerKm
     */
    fun getFuelUsagePerKm(): MutableLiveData<Double> {
        return fuelUsagePerKm
    }

    /**
     * Function that gets the local gasoline variable
     * @return returns a certain which presents a certain gasoline object
     */
    fun getGasoline(): MutableLiveData<Gasoline> {
        return gasoline
    }

    /**
     * Function that gets the local refundTravelExpensesPerKm variable
     * @return returns a certain value which presents your refunded travelExpenses per km
     */
    fun getRefundTravelExpensesPerKm(): MutableLiveData<Double> {
        return refundTravelExpensesPerKm
    }
}