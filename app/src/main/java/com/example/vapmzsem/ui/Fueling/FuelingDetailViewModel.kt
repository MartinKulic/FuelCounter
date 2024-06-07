package com.example.vapmzsem.ui.Fueling

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vapmzsem.data.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class FuelingDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: AppRepository
) : ViewModel(){

    private val fuelingId: Int = checkNotNull(savedStateHandle[FuelingDetailDestination.fuelingIdArg])

    val uiState: StateFlow<FuelingAsUi> = repository.getFueling(fuelingId).filterNotNull().map {
        it.toUi()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = FuelingAsUi()
    )




    suspend fun delete(){
        repository.delete(uiState.value.toFueling())
    }

    companion object{
        private const val TIMEOUT_MILLIS = 5_000L
    }
}
