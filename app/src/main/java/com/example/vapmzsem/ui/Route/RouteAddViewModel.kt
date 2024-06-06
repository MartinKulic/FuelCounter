package com.example.vapmzsem.ui.Route

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.vapmzsem.data.AppRepository
import com.example.vapmzsem.ui.Fueling.FuelingAsUi

class RouteAddViewModel(
    private val repository: AppRepository
) : ViewModel(){
    var routeUiState by mutableStateOf(RouteAddUiState(details = RouteAsUi()))
    private set

    fun updateUiState (routeDetail: RouteAsUi){
        //fuelingUiState = FuelingUiState(isEntryValid = validateInput(fueligDetail), details = fueligDetail)
        routeUiState = routeUiState.copy(isEntryValid = validateInput(routeDetail), details = routeDetail)
    }

    fun validateInput(details : RouteAsUi = routeUiState.details) : Boolean{
        return with(details){
            distance.isNotBlank() && finish_odometer.isNotBlank()
        }
    }

    fun updatedDistance(sdistance : String){

    }
    fun updatedOdometer(sodometer: String){

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