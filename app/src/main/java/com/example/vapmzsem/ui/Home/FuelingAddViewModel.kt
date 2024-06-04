package com.example.vapmzsem.ui.Home


import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vapmzsem.data.AppRepository
import com.example.vapmzsem.data.Fueling
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar

class FuelingAddViewModel(
    private val repository: AppRepository
) : ViewModel() {
    val lastFueling : StateFlow<FuelingAsUi> = repository.getNewestFueling()
        //.filterNotNull()
        .map { it.toUi() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = FuelingAsUi()
        )
    /*TODO Nezobrazi poslendy odometer*/
    var fuelingUiState by mutableStateOf(FuelingUiState(details = FuelingAsUi(odometter = lastFueling.value.odometter)))
        private set



    fun updateUiState (fueligDetail: FuelingAsUi){
        fuelingUiState = FuelingUiState(isEntryValid = validateInput(fueligDetail), details = fueligDetail)
    }
    fun validateInput(details : FuelingAsUi = fuelingUiState.details) : Boolean{
        return with(details){
            quantity.isNotBlank() && quantity.toFloatOrNull() != 0f && total_price.isNotBlank() && odometter >= "0"
        }
    }

    suspend fun saveFueling(){
        if(validateInput()){
            repository.insert(fuelingUiState.details.toFueling())
        }
    }
}

data class FuelingUiState(
    val isEntryValid : Boolean = false,
    val details: FuelingAsUi = FuelingAsUi()

)
