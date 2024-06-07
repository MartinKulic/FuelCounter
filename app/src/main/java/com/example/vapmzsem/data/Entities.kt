package com.example.vapmzsem.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date


@Entity (tableName = "fuelings")
data class Fueling (
    @PrimaryKey(autoGenerate = true)
    val id_F: Int = 0,
    val quantity : Float,
    val total_price : Float,
    val full_tank : Boolean = false,
    val fuel_type : String? = null,
    val fueling_Station : String? = null,
    val time : Date = Date(),
    val odometer: Int? = null,
    val distance_traveled : Float = 0f,
    val average_fuel_consumption : Float = 4.7f
)

@Entity(tableName = "routes", foreignKeys = [ForeignKey(entity = Fueling::class, parentColumns = ["id_F"], childColumns = ["id_F"])])
data class Route(
    @PrimaryKey(autoGenerate = true)
    val id_R : Int = 0,
    val id_F : Int?,
    val title : String,
    val distance : Float,
    val start_time: Date,
    val finish_time : Date,
    val start_point : String?,
    val finish_point : String?,
    val finish_odometer: Int
)