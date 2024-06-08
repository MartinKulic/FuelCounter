package com.example.vapmzsem.ui.Home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vapmzsem.data.AppRepository
import com.example.vapmzsem.data.Fueling
import com.example.vapmzsem.ui.Fueling.FuelingAsUi
import com.example.vapmzsem.ui.Fueling.toUi
import com.example.vapmzsem.ui.Route.RouteAsUi
import com.example.vapmzsem.ui.Route.toUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class HomeViewModel(
    repository: AppRepository,
    averageFuelConsumption : Flow<Float>
) : ViewModel(){
    val homeUiState : StateFlow<HomeUiState> = repository.getAllFuelings().map { fuelings ->
        val firstFueling : Fueling = fuelings.first()
        HomeUiState( data = fuelings.map { fuel -> MyContainer(
            fueling = fuel.toUi(),
            routes = repository.getAllRoutesToFueling(fuel.id_F).first().sortedBy { it.start_time }.reversed().map {
                var fuelConsumption = fuel?.fuel_consumption ?: averageFuelConsumption.first()
                if (fuel == firstFueling){
                    fuelConsumption = averageFuelConsumption.first()
                }
                val fuelUsed = (it.distance * fuelConsumption)/100f
                var price = if (fuel==null) null else fuelUsed * (fuel.total_price/fuel.quantity)

                val stringedCounsumption = DecimalFormat("##.000").format(fuelConsumption)
                val stringedFuelUsed = DecimalFormat("#,###.00").format(fuelUsed)
                val stringedPrice = if (price==null) "-" else DecimalFormat("#,###.00").format(price)

                 it.toUi().copy(fuel_used = stringedFuelUsed, fuel_consumption = stringedCounsumption, cost_of_route = stringedPrice)

            }
            )
         }
        )
    }. stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = HomeUiState()
    )
}


data class HomeUiState(
    val data : List<MyContainer> = listOf()
)

data class MyContainer(
    val fueling : FuelingAsUi = FuelingAsUi(),
    val routes : List<RouteAsUi> = listOf()
)