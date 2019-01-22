package com.example.alexander.ridy.Model.persistence.Ride

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.example.alexander.ridy.Model.domain.Gasoline
import com.example.alexander.ridy.Model.domain.Ride
import com.example.alexander.ridy.Model.extensions.VehicleTypeConverter

/**
 * The Ride database class is a room database which holds entities(objects) of rides and gasolines
 */
@Database(entities = [Ride::class, Gasoline::class], version = 1)
@TypeConverters(VehicleTypeConverter::class)
abstract class RideDatabase : RoomDatabase() {
    /**
     * Function that returns a rideDAO instance
     * @return ridao instance
     */
    abstract fun rideDAO() : RideDAO

    /**
     * This object is a singleton onject that has to be called by its named. Every method can be used in other classes
     * @property INSTANCE represents an instance of the ride database
     */
    companion object {
        @Volatile
        private var INSTANCE : RideDatabase? = null

        /**
         * Function that returns an instance of the rideDatabase
         * @return a ride database
         */
        fun getInstance(context : Context) : RideDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext ,
                    RideDatabase::class.java,
                    "Ride_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}