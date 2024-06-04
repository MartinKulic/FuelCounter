package com.example.vapmzsem.ui.Fueling

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vapmzsem.data.AppRepository
import com.example.vapmzsem.data.Fueling
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FuelinViewModel(
    itemsRepository : AppRepository
) : ViewModel() {
    val fuelingScreenUiState : StateFlow<FuelingScreenUiState> = itemsRepository.getAllFuelings().map {
        FuelingScreenUiState(it)}.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = FuelingScreenUiState()
        )

    companion object{
        private const val TIMEOUT_MILLIS = 5_000L
    }
}
data class FuelingScreenUiState(
    val itemList : List<Fueling> = listOf()
)