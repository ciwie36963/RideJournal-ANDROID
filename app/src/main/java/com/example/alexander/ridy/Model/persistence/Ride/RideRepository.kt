package com.example.alexander.ridy.Model.persistence.Ride

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import android.util.Log
import com.example.alexander.ridy.Model.domain.Gasoline
import com.example.alexander.ridy.Model.domain.GasolineType
import com.example.alexander.ridy.Model.domain.Ride

/**
 * The repository adds another abstraction layer between the DAO and the ViewModel
 * @param rideDao it requires the rideDao the perform its queries
 * @property rides A list of all the rides, liveData is used because it can monitor changes
 * @property gasolines A list of all the gasolines, liveData is used because it can monitor changes
 */
class RideRepository(private val rideDao : RideDAO) {

    val rides : LiveData<List<Ride>> = rideDao.getAllRides()
    var gasolines : LiveData<List<Gasoline>> = rideDao.getAllGasolines()

    /**
     * Function that inserts a ride into the local database
     * @param ride represents a Ride object
     */
    @WorkerThread
    fun insert(ride : Ride) {
        rideDao.insert(ride)
    }

    /**
     * Function that deletes a ride into the local database
     * @param ride represents a Ride object
     */
    @WorkerThread
    fun delete(ride : Ride) {
        rideDao.delete(ride)
    }

    /**
     * Function that deletes all ride from the local database
     */
    @WorkerThread
    fun deleteAll() {
        rideDao.deleteAll()
    }

    /**
     * Function that inserts a gasoline into the local database
     * @param gasoline represents a Gasoline object
     */
    @WorkerThread
    fun insert(gasoline: Gasoline) {
        rideDao.insert(gasoline)
    }

    /**
     * Function that deletes all gasolines from the local database
     */
    @WorkerThread
    fun deleteAllGasolines() {
        rideDao.deleteAllGasolines()
    }

    /**
     * Function that updates a [price] from a certain [gasolineType] in the database
     * @param price price of the gasoline
     * @param gasolineType type of the gasoline
     * @return returns a Livedata List from the gasolines, LiveData is used because it can monitor changes
     */
    @WorkerThread
    fun updatePriceGasoline(prijs: Double, gasolineType: GasolineType) {
        rideDao.updatePriceGasoline(prijs, gasolineType)
    }
}