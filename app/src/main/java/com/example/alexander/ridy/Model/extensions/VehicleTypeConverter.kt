package com.example.alexander.ridy.Model.extensions

import android.arch.persistence.room.TypeConverter
import com.example.alexander.ridy.Model.domain.VehicleType

/**
 * Class that converts an enum to a string and <> for the database
 */
class VehicleTypeConverter {

    /**
     * Function that converts an enum to a string
     * @param vehicleType the vehicleType to transform
     * @return returns the vehicleType in string format
     */
    @TypeConverter
    fun fromEnumToString(vehicleType: VehicleType) : String {
        when(vehicleType) {
            VehicleType.CAR -> return "Car"
            VehicleType.BIKE -> return "Bike"
        }
    }

    /**
     * Function that converts a string to an enum
     * @param vehicleType the vehicleType to transform
     * @return returns the vehicleType in enum format
     */
    @TypeConverter
    fun fromStringToEnum(vehicleType : String) : VehicleType {
        when(vehicleType) {
            "Car" -> return VehicleType.CAR
            "Bike" -> return VehicleType.BIKE
        }
        //because wouldn't run without standard return
        return VehicleType.BIKE
    }
}