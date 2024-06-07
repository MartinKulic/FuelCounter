package com.example.vapmzsem.ui.Route

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vapmzsem.data.AppRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RouteDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: AppRepository
) : ViewModel(){

    private val routeId: Int = checkNotNull(savedStateHandle[RouteDetailDestinantion.routeIdArg])
    val uiState : StateFlow<RouteAsUi> = repository.getRoute(routeId)
        .filterNotNull()
        .map { it.toUi() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = RouteAsUi()
        )

    suspend fun delete(){
        repository.delete(uiState.value.toRoute())
    }
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}