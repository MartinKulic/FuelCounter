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
) : RouteModifieInterface(){

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
    suspend fun updateRoute(){
        if(validateInput()){
            repository.update(routeUiState.details.toRoute())
        }
    }


}