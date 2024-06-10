package com.example.vapmzsem.ui.Statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vapmzsem.data.AppRepository
import com.example.vapmzsem.ui.Route.RouteAsUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class StatisticViewModel(
    private val repository: AppRepository,
    val averageFuelConsumption : Flow<Float>
):ViewModel(){
    private val _uiState = MutableStateFlow(StatisticUiState())
    val uiState : StateFlow<StatisticUiState> = _uiState

    init {
        viewModelScope.launch {
            var avgComsumption: Float = averageFuelConsumption.first()
            var avgFuelPrice : Float = 0f
            var avgPayedForFuel : Float = 0f
            var avgDistanceBetweenFuelings : Float = 0f
            var avgQuality :Float = 0f
            var totalFuelBuyed : Float = 0f
            var totalDistanceTraveled : Float = 0f

            repository.getAllFuelings().collect { fuelings ->
                for (fueling in fuelings){
                    avgFuelPrice += fueling.total_price/fueling.quantity
                    avgPayedForFuel += fueling.total_price
                    avgDistanceBetweenFuelings += fueling.distance_traveled
                    avgQuality += fueling.quantity
                    totalFuelBuyed += fueling.quantity
                    totalDistanceTraveled += fueling.distance_traveled
                }
//                for (route in routes){
//
//                }

                    avgFuelPrice /= fuelings.size
                    avgPayedForFuel /= fuelings.size
                    avgDistanceBetweenFuelings /= fuelings.size
                    avgQuality /= fuelings.size


                val stringified_avgComsumption : String = DecimalFormat("##.000").format(avgComsumption)
                val stringified_avgFuelPrice : String = DecimalFormat("##.000").format(avgFuelPrice)
                val stringified_avgPayedForFuel : String = "%.2f".format(avgPayedForFuel)
                val stringified_avgDistanceBetweenFuelings : String = "%.1f".format(avgDistanceBetweenFuelings)
                val stringified_avgQuality : String = "%.3f".format(avgQuality)
                val stringified_totalFuelBuyed : String = "%.0f".format(totalFuelBuyed)
                val stringified_totalDistanceTraveled : String = "%.0f".format(totalDistanceTraveled)

                _uiState.value = StatisticUiState(
                    averageConsummption = stringified_avgComsumption,
                    avarageFuelPrice = stringified_avgFuelPrice,
                    averagePayedForFuel = stringified_avgPayedForFuel,
                    averageDistanceBetweenFuelings = stringified_avgDistanceBetweenFuelings,
                    averageQuantity = stringified_avgQuality,
                    totalFuelBuyed = stringified_totalFuelBuyed,
                    totalDistanceTraveled = stringified_totalDistanceTraveled
                )
            }
        }
    }
}
data class StatisticUiState(
    val averageConsummption : String = "",
    val avarageFuelPrice : String = "",
    val averagePayedForFuel : String = "",
    val averageDistanceBetweenFuelings : String = "",
    val averageQuantity : String = "",
    val totalFuelBuyed : String = "",
    val totalDistanceTraveled : String = ""
)