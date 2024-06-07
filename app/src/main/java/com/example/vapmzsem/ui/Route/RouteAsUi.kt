package com.example.vapmzsem.ui.Route

import com.example.vapmzsem.data.Route
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

data class RouteAsUi(
    val id : Int = 0,
    val id_F : Int? = null,
    val title : String = "",
    val distance : String = "",
    val start_time : Calendar = Calendar.getInstance(),
    val finish_time : Calendar =  Calendar.getInstance(),
    val start_point : String = "",
    val finish_point : String = "",
    val start_odometer : String = "",
    val finish_odometer : String = "",
    var fuel_used : String = "-",
    var cost_of_route : String = "-",
    var fuel_consumption : String = "-"
){
    init {
        if (start_time == finish_time) {
            finish_time.add(Calendar.HOUR_OF_DAY, 1)
        }
    }

    fun toRoute() : Route {
        val fixTitle : String =
        if (title.isEmpty()){
            SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault()).format(finish_time.time)
        } else {
            title
        }

        return Route(
            id_R = id,
            id_F = id_F,
            title = fixTitle,
            distance = distance.toFloatOrNull() ?: 0f,
            start_time = start_time.time,
            finish_time = finish_time.time,
            start_point = start_point,
            finish_point = finish_point,
            finish_odometer = finish_odometer.toIntOrNull() ?: 0
        )
    }
}
fun Route.toUi() : RouteAsUi{
    val startTime = Calendar.getInstance()
    startTime.timeInMillis = start_time.time
    val finishTime = Calendar.getInstance()
    finishTime.timeInMillis = finish_time.time
    return RouteAsUi (
        id = id_R,
        id_F = id_F,
        title = title,
        distance = distance.toString(),
        start_time = startTime,
        finish_time = finishTime,
        start_point = start_point ?: "",
        finish_point = finish_point ?: "",
        finish_odometer = finish_odometer.toString(),
        start_odometer = (finish_odometer - distance).toString()
    )
}
