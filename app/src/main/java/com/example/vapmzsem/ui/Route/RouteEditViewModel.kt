package com.example.vapmzsem.ui.Route

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vapmzsem.data.AppRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class RouteEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: AppRepository
) : ViewModel(){
    var routeUiState by mutableStateOf(RouteAddUiState(details = RouteAsUi()))
        private set
    private val routeId: Int = checkNotNull(savedStateHandle[RouteEditDestination.routeIdArg])

    init {
        viewModelScope.launch {
            updateUiState(repository.getRoute(routeId)
                .filterNotNull()
                .first()
                .toUi()
            )
        }


    }
    fun updateUiState(routeAsUi : RouteAsUi){
        routeUiState = RouteAddUiState(isEntryValid = validateInput(routeAsUi), details=routeAsUi)
    }
    private fun validateInput(details : RouteAsUi = routeUiState.details) : Boolean{
        return with(details){
            distance.isNotBlank() && finish_odometer.isNotBlank()
        }
    }

    suspend fun updateRoute(){
        if(validateInput()){
            repository.update(routeUiState.details.toRoute())
        }
    }
}