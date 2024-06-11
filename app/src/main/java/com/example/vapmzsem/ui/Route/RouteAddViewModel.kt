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
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class RouteAddViewModel(
    private val repository: AppRepository
) : RouteModifieInterface() {

    init {
        viewModelScope.launch {
            val previousOdometer = repository.getNewestRoute().first()?.finish_odometer ?: repository.getNewestFueling().first()?.odometer

            updateUiState(routeUiState.details.copy(finish_odometer = previousOdometer?.toString()?:""))
        }
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