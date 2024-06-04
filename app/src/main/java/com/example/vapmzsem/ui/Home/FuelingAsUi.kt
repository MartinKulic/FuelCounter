package com.example.vapmzsem.ui.Home

import com.example.vapmzsem.data.Fueling
import java.util.Calendar

data class FuelingAsUi(
    val id : Int = 0,
    val quantity : String = "",
    val total_price : String = "",
    val full_tank : Boolean = false,
    val fuel_type : String = "",
    val fueling_Station : String = "",
    val time : Calendar = Calendar.getInstance(),
    val odometter: String = ""
){
    fun toFueling() : Fueling {
        return Fueling(
            quantity = quantity.replace(",",".").toFloatOrNull() ?: 1f,
            total_price = total_price.replace(",",".").toFloatOrNull() ?: 1f,
            full_tank = full_tank,
            fuel_type = fuel_type,
            fueling_Station = fueling_Station,
            time = time.time,
            odometter = odometter.toIntOrNull() ?: 0
        )
    }
}

fun Fueling.toUi() : FuelingAsUi{
    val calTime = Calendar.getInstance()
    calTime.timeInMillis = time.time
    return FuelingAsUi(
        id = id_F,
        quantity = quantity.toString(),
        total_price = total_price.toString(),
        full_tank = full_tank,
        fuel_type = fuel_type ?: "",
        fueling_Station = fueling_Station ?: "",
        time = calTime,
        odometter = odometter.toString()
    )
}
