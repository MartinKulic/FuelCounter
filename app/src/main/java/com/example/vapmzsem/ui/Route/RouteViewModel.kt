package com.example.vapmzsem.ui.Route

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vapmzsem.data.AppRepository
import com.example.vapmzsem.data.Fueling
import com.example.vapmzsem.data.Route
import com.example.vapmzsem.ui.Fueling.FuelingScreenUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RouteViewModel (repository : AppRepository) : ViewModel() {
    val routeScreenUiState : StateFlow<RouteScreenUiState> = repository.getAllRoutes().map { routeList ->
        RouteScreenUiState(routeList.map { it.toUi() })
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = RouteScreenUiState()
    )
    companion object{
        private const val TIMEOUT_MILLIS = 5_000L
    }
}
data class RouteScreenUiState(
    val routesList: List<RouteAsUi> = listOf()
)