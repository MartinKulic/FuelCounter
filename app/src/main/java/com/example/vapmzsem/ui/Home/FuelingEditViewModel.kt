package com.example.vapmzsem.ui.Home

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vapmzsem.MyTopAppBar
import com.example.vapmzsem.data.AppRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class FuelingEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: AppRepository
) : ViewModel(){

    var fuelingUiState by mutableStateOf(FuelingUiState(details = FuelingAsUi()))
        private set
    private val fuelingId: Int = checkNotNull(savedStateHandle[FuelingEditScreenDestination.fuelingIdArg])

    init {
        viewModelScope.launch {
            updateUiState(repository.getFueling(fuelingId)
                .filterNotNull()
                .first()
                .toUi()
            )
        }
    }

    fun updateUiState(details : FuelingAsUi){
        fuelingUiState = fuelingUiState.copy(isEntryValid = validateInput(details), details = details)
    }

    private fun validateInput(details : FuelingAsUi = fuelingUiState.details) : Boolean{
        return with(details){
            quantity.isNotBlank() && quantity.toFloatOrNull() != 0f && total_price.isNotBlank() && odometter >= "0"
        }
    }

    suspend fun saveFueling(){
        if (validateInput()){
            repository.update(fuelingUiState.details.toFueling())
        }
    }
}