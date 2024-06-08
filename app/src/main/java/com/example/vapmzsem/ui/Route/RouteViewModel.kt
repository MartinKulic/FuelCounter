package com.example.vapmzsem.ui.Route


import androidx.compose.ui.unit.times
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vapmzsem.data.AppRepository
import com.example.vapmzsem.data.Fueling
import com.example.vapmzsem.data.Route
import com.example.vapmzsem.ui.Fueling.FuelingScreenUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class RouteViewModel (
    repository : AppRepository,
    val averageFuelConsumption : Flow<Float>
) : ViewModel() {
    val _routeScreenUiState : MutableStateFlow<RouteScreenUiState> = MutableStateFlow(
        RouteScreenUiState()
    )
    val routeScreenUiState : StateFlow<RouteScreenUiState> = _routeScreenUiState.asStateFlow()
//    val routeScreenUiState : StateFlow<RouteScreenUiState> = repository.getAllRoutes().map { routeList ->
//        RouteScreenUiState(routeList.map { it.toUi() })
//    }.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
//        initialValue = RouteScreenUiState()
//    )
    init{
        viewModelScope.launch {
            //var fuelConsumption = averageFuelConsumption.first()
            repository.getAllRoutes().combine(repository.getAllFuelings()) { routes, fuelings ->
                routes.map {route ->
                    val corespondingFueling = fuelings.find { it.id_F == route.id_F }

                    var fuelConsumption = corespondingFueling?.fuel_consumption ?: averageFuelConsumption.first()
                    if (corespondingFueling == repository.getNewestFueling().first()){
                        fuelConsumption = averageFuelConsumption.first()
                    }
                        val fuelUsed = (route.distance * fuelConsumption)/100f
                        var price = if (corespondingFueling==null) null else fuelUsed * (corespondingFueling.total_price/corespondingFueling.quantity)

                        val stringedCounsumption = DecimalFormat("##.000").format(fuelConsumption)
                        val stringedFuelUsed = DecimalFormat("#,###.00").format(fuelUsed)
                        val stringedPrice = if (price==null) "-" else DecimalFormat("#,###.00").format(price)

                        route.toUi().copy(fuel_used = stringedFuelUsed, fuel_consumption = stringedCounsumption, cost_of_route = stringedPrice)

                }
            }.collect{
                updatedRoutes -> _routeScreenUiState.value = RouteScreenUiState(updatedRoutes)
            }

        }
    }

    companion object{
        private const val TIMEOUT_MILLIS = 5_000L
    }
}
data class RouteScreenUiState(
    val routesList: List<RouteAsUi> = listOf(),
)