package com.example.vapmzsem.ui.Fueling

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vapmzsem.data.AppRepository
import com.example.vapmzsem.data.Fueling
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class FuelinViewModel(
    itemsRepository : AppRepository
) : ViewModel() {
    val fuelingScreenUiState : StateFlow<FuelingScreenUiState> = itemsRepository.getAllFuelings().map {
        fuelings -> FuelingScreenUiState(fuelings.map{it.toUi()})}.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = FuelingScreenUiState()
        )
    init {
        val fuelings = fuelingScreenUiState.value.itemList
        for(fueling in fuelings) {
            viewModelScope.launch {
                val routesToFueling = itemsRepository.getAllRoutesToFueling(fueling.id).first()
                var distanceTraveled = 0f
                routesToFueling.forEach{ route -> distanceTraveled += route.distance }
                fueling.distance = DecimalFormat(
                    "###,###", DecimalFormatSymbols(
                        Locale.getDefault()
                    )
                    ).format(distanceTraveled)
            }
        }
    }

    companion object{
        private const val TIMEOUT_MILLIS = 5_000L
    }
}
data class FuelingScreenUiState(
    val itemList : List<FuelingAsUi> = listOf()
)