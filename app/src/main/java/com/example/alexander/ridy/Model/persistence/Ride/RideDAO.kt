package com.example.alexander.ridy.Model.persistence.Ride

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.persistence.room.*
import com.example.alexander.ridy.Model.domain.Gasoline
import com.example.alexander.ridy.Model.domain.GasolineType
import com.example.alexander.ridy.Model.domain.Ride
import com.example.alexander.ridy.Model.extensions.GasolineTypeConverter

/**
 * Interface which has the functions to perform queries on the local database
 */
@Dao
interface RideDAO {

    /**
     * Function that inserts a ride into the local database
     * @param ride represents a Ride object
     */
    @Insert
    fun insert(ride: Ride)

    /**
     * Function that inserts a gasoline into the local database
     * @param gasoline represents a Gasoline object
     */
    @Insert
    fun insert(gasoline: Gasoline)

    /**
     * Function that deletes a ride into the local database
     * @param ride represents a Ride object
     */
    @Delete
    fun delete(ride: Ride)

    /**
     * Function that deletes all ride from the local database
     */
    @Query("DELETE FROM ride_table")
    fun deleteAll()

    /**
     * Function that deletes all gasolines from the local database
     */
    @Query("DELETE FROM gasoline_table")
    fun deleteAllGasolines()

    /**
     * Function that retrieves all rides from the local database
     * @return returns a Livedata List from the rides, LiveData is used because it can monitor changes
     */
    @Query("SELECT * FROM ride_table")
    fun getAllRides() : LiveData<List<Ride>>

    /**
     * Function that retrieves all gasolines from the local database
     * @return returns a Livedata List from the gasolines, LiveData is used because it can monitor changes
     */
    @Query("SELECT * FROM gasoline_table")
    fun getAllGasolines() : LiveData<List<Gasoline>>

    /**
     * Function that updates a [price] from a certain [gasolineType] in the database
     * @param price price of the gasoline
     * @param gasolineType type of the gasoline
     * @return returns a Livedata List from the gasolines, LiveData is used because it can monitor changes
     */
    @TypeConverters(GasolineTypeConverter::class)
    @Query("UPDATE gasoline_table SET price = :price WHERE gasolineType = :gasolineType")
    fun updatePriceGasoline(price: Double, gasolineType: GasolineType)
}