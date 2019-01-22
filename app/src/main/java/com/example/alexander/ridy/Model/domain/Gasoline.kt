package com.example.alexander.ridy.Model.domain

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.example.alexander.ridy.Model.extensions.GasolineTypeConverter
import com.example.alexander.ridy.Model.extensions.VehicleTypeConverter
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

/**
 * Class that represents a Gasoline object
 * @property id id of the gasoline object
 * @property price price of the gasoline object
 * @property gasolineType type of the gasoline object
 * @constructor 2 constructors, one for the simpleXML framework and one the make the Gasoline object
 */
@Root(strict = false)
@Entity(tableName = "gasoline_table")
class Gasoline {

    @PrimaryKey(autoGenerate = true )
    var id : Int = 0

    @field:Element(name="price")
    var price : Double = 0.0

    @field:Element(name="type")
    @TypeConverters(GasolineTypeConverter::class)
    var gasolineType : GasolineType = GasolineType.Super95

    //Constructor for the SimpleXMLFramework
    constructor()

    constructor(price: Double, gasolineType: GasolineType) {
        this.price = price
        this.gasolineType = gasolineType
    }
}