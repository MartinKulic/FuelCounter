package com.example.vapmzsem.ui.Fueling

import com.example.vapmzsem.data.Fueling
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Calendar
import java.util.Locale

data class FuelingAsUi(
    val id : Int = 0,
    val quantity : String = "",
    val total_price : String = "",
    val full_tank : Boolean = true,
    val fuel_type : String = "",
    val fueling_Station : String = "",
    val time : Calendar = Calendar.getInstance(),
    val odometter: String = "",
    val distance: String = "-", // Vzdialenst prejdena na tankovanie
    val average_fuel_consumption : String = "-",
    val price_per_liter : String = "-"
){
    fun toFueling() : Fueling {
        return Fueling(
            id_F = id,
            quantity = quantity.replace(",",".").toFloatOrNull() ?: 1f,
            total_price = total_price.replace(",",".").toFloatOrNull() ?: 1f,
            full_tank = full_tank,
            fuel_type = fuel_type,
            fueling_Station = fueling_Station,
            time = time.time,
            odometer =  odometter.toFloatOrNull()?.toInt() ?: 0,
        )
    }

}

fun Fueling.toUi(distanceTraveled : String = "-") : FuelingAsUi{
    val calTime = Calendar.getInstance()
    calTime.timeInMillis = time.time
    return FuelingAsUi(
        id = id_F,
        quantity = DecimalFormat(
            "###,###.00", DecimalFormatSymbols(
                Locale.getDefault()
            )
        ).format(quantity),
        total_price =  DecimalFormat(
            "###,###.00", DecimalFormatSymbols(
                Locale.getDefault()
            )
        ).format(total_price),
        full_tank = full_tank,
        fuel_type = fuel_type ?: "",
        fueling_Station = fueling_Station ?: "",
        time = calTime,
        odometter = DecimalFormat(
            "###,###,###", DecimalFormatSymbols(
                Locale.getDefault()
            )
        ).format(odometer),
        distance = DecimalFormat(
            "###,###.00", DecimalFormatSymbols(
                Locale.getDefault()
            )
        ).format(distance_traveled),
        average_fuel_consumption = DecimalFormat(
            "###,###.00", DecimalFormatSymbols(
                Locale.getDefault()
            )
        ).format(fuel_consumption),
        price_per_liter = DecimalFormat(
            "###,###.000", DecimalFormatSymbols(
                Locale.getDefault()
            )
        ).format(total_price / quantity)
    )
}
