package com.example.vapmzsem.ui.Route

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vapmzsem.data.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class RouteDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: AppRepository,
    val averageFuelConsumption : Flow<Float>
) : ViewModel(){

    private val routeId: Int = checkNotNull(savedStateHandle[RouteDetailDestinantion.routeIdArg])
    val _uiState = MutableStateFlow(RouteAsUi())
    val uiState : StateFlow<RouteAsUi> = _uiState

    init{
        viewModelScope.launch {
            val route = repository.getRoute(routeId).first()
            val fueling = repository.findCorespondingFueling(route.id_F)

            var fuelConsumption = fueling?.fuel_consumption ?: averageFuelConsumption.first()
            if (fueling == repository.getNewestFueling().first()){
                fuelConsumption = averageFuelConsumption.first()
            }
            val fuelUsed = (route.distance * fuelConsumption)/100f
            val price = if (fueling==null) null else fuelUsed * (fueling.total_price/fueling.quantity)

            val stringedCounsumption = DecimalFormat("##,###.000").format(fuelConsumption)
            val stringedFuelUsed = DecimalFormat("##,###.000").format(fuelUsed)
            val stringedPrice = if (price==null) "-" else DecimalFormat("##,###.00").format(price)

            _uiState.value = route.toUi().copy(fuel_used = stringedFuelUsed, fuel_consumption = stringedCounsumption, cost_of_route = stringedPrice)
        }
    }
    suspend fun delete(){
        repository.delete(uiState.value.toRoute())
    }
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}