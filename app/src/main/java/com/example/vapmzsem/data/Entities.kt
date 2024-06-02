package com.example.vapmzsem.data

import androidx.compose.foundation.pager.PagerSnapDistance
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
    val full_tank : Boolean,
    val fuel_type : String?,
    val fueling_Station : String?,
    val time : Date?,
    val odometter: Int?
)

@Entity(tableName = "routes", foreignKeys = [ForeignKey(entity = Fueling::class, parentColumns = ["id_F"], childColumns = ["id_F"])])
data class Route(
    @PrimaryKey(autoGenerate = true)
    val id_R : Int = 0,
    val id_F : Int?,
    val distance : Float,
    val start_time: Date,
    val finish_time : Date,
    val start_point : String?,
    val finish_point : String?,
    val start_odometter: Int?
)