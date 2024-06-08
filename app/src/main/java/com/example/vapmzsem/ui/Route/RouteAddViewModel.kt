package com.example.vapmzsem.ui.Route

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vapmzsem.data.AppRepository
import com.example.vapmzsem.ui.Fueling.FuelingAsUi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class RouteAddViewModel(
    private val repository: AppRepository
) : ViewModel() , RouteModifieInterface {
    var routeUiState by mutableStateOf(RouteAddUiState(details = RouteAsUi()))
    private set

    init {
        viewModelScope.launch {
            val previousOdometer = repository.getNewestRoute().first()?.finish_odometer ?: repository.getNewestFueling().first()?.odometer

            updateUiState(routeUiState.details.copy(finish_odometer = previousOdometer.toString()))
        }
    }

    fun updateUiState (routeDetail: RouteAsUi){
        //fuelingUiState = FuelingUiState(isEntryValid = validateInput(fueligDetail), details = fueligDetail)
        routeUiState = routeUiState.copy(isEntryValid = validateInput(routeDetail), details = routeDetail)
    }

    fun validateInput(details : RouteAsUi = routeUiState.details) : Boolean{
        return with(details){
            distance.isNotBlank() && finish_odometer.isNotBlank()
        }
    }

    override fun updatedDistance(sdistance : String){
        val diference = ((sdistance.toFloatOrNull() ?: 0f) - (routeUiState.details.distance.toFloatOrNull() ?: 0f))
        updateUiState(routeUiState.details.copy(distance = sdistance, finish_odometer = ((routeUiState.details.finish_odometer.toFloatOrNull() ?: 0f) + diference).toString()))
    }
    override fun updatedOdometer(sodometer: String){
        val diference = ((routeUiState.details.finish_odometer.toFloatOrNull() ?: 0f) - (sodometer.toFloatOrNull() ?: 0f))
        updateUiState(routeUiState.details.copy(distance = ((routeUiState.details.distance.toFloatOrNull() ?: 0f) + diference).toString(), finish_odometer = sodometer))
    }

    suspend fun saveRoute(){
        if(validateInput()){
            repository.insert(routeUiState.details.toRoute())
        }
    }
}

data class RouteAddUiState(
    val isEntryValid : Boolean = false,
    val details: RouteAsUi = RouteAsUi(),
)