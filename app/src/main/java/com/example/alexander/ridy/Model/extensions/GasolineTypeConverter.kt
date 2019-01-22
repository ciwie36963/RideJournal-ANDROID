package com.example.alexander.ridy.Model.extensions

import android.arch.persistence.room.TypeConverter
import com.example.alexander.ridy.Model.domain.GasolineType
import com.example.alexander.ridy.Model.domain.VehicleType

/**
 * Class that converts an enum to a string and <> for the database
 */
class GasolineTypeConverter {

    /**
     * Function that converts an enum to a string
     * @param gasolineType the gasolineType to transform
     * @return returns the gasolineType in string format
     */
    @TypeConverter
    fun fromEnumToString(gasolineType: GasolineType) : String {
        when(gasolineType) {
            GasolineType.Diesel -> return "Diesel"
            GasolineType.Super95 -> return "Super95"
            GasolineType.LPG -> return "LPG"
        }
    }

    /**
     * Function that converts a string to an enum
     * @param gasolineType the gasolineType to transform
     * @return returns the gasolineType in enum format
     */
    @TypeConverter
    fun fromStringToEnum(gasolineType : String) : GasolineType {
        when(gasolineType) {
            "Diesel" -> return GasolineType.Diesel
            "Super95" -> return GasolineType.Super95
            "LPG" -> return GasolineType.LPG
        }
        //because wouldn't run without standard return
        return GasolineType.LPG
    }
}