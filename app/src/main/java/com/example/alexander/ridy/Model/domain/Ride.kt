package com.example.alexander.ridy.Model.domain

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.example.alexander.ridy.Model.extensions.VehicleTypeConverter

/**
 * Class that represents a Ride
 * @property id id of the ride object
 * @property distanceRide distance of the ride
 * @property vehicle the vehicle used in the ride
 * @property moneySaved the money saved in the ride
 * @property rideToWork determines if it's a ride to work or not
 * @constructor Constructor to create the ride object
 */
@Entity(tableName = "ride_table")
class Ride {

    @PrimaryKey(autoGenerate = true )
    var id : Int = 0

    var distanceRide : Double

    @TypeConverters(VehicleTypeConverter::class)
    var vehicle : VehicleType

    var moneySaved : Double
    var rideToWork : Boolean

  constructor(
        distanceRide: Double,
        vehicle: VehicleType,
        moneySaved: Double,
        rideToWork: Boolean
    ) {
        this.distanceRide = distanceRide
        this.vehicle = vehicle
        this.moneySaved = moneySaved
        this.rideToWork = rideToWork
    }
}