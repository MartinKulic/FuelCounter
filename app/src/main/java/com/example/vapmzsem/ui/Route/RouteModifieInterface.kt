package com.example.vapmzsem.ui.Route

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

abstract class RouteModifieInterface() : ViewModel() {

    var routeUiState by mutableStateOf(RouteAddUiState(details = RouteAsUi()))
        private set
    fun updatedDistance(sdistance: String) {
        val diference =
            ((sdistance.toFloatOrNull() ?: 0f) - (routeUiState.details.distance.toFloatOrNull()
                ?: 0f))
        updateUiState(
            routeUiState.details.copy(
                distance = sdistance,
                finish_odometer = ((routeUiState.details.finish_odometer.toFloatOrNull()
                    ?: 0f) + diference).toString()
            )
        )
    }

    fun updatedOdometer(sodometer: String) {
        val diference = ((routeUiState.details.finish_odometer.toFloatOrNull()
            ?: 0f) - (sodometer.toFloatOrNull() ?: 0f))
        updateUiState(
            routeUiState.details.copy(
                distance = ((routeUiState.details.distance.toFloatOrNull()
                    ?: 0f) + diference).toString(), finish_odometer = sodometer
            )
        )
    }

    fun updateUiState(routeDetail: RouteAsUi) {
        //fuelingUiState = FuelingUiState(isEntryValid = validateInput(fueligDetail), details = fueligDetail)
        routeUiState =
            routeUiState.copy(isEntryValid = validateInput(routeDetail), details = routeDetail)
    }

    fun validateInput(details: RouteAsUi = routeUiState.details): Boolean {
        return with(details) {
            distance.isNotBlank() && finish_odometer.isNotBlank()
        }

    }
}