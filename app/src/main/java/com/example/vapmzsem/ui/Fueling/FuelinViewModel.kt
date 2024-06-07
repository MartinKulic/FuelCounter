package com.example.vapmzsem.ui.Fueling

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vapmzsem.data.AppRepository
import com.example.vapmzsem.data.Fueling
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class FuelinViewModel(
    private val itemsRepository : AppRepository
) : ViewModel() {

    //val _fuelingScreenUiState = MutableStateFlow(FuelingScreenUiState())
    //val fuelingScreenUiState : StateFlow<FuelingScreenUiState> = _fuelingScreenUiState

    val fuelingScreenUiState : StateFlow<FuelingScreenUiState>
    init {
        fuelingScreenUiState = itemsRepository.getAllFuelings().map {
                fuelings -> FuelingScreenUiState(fuelings.map{it.toUi()})}.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = FuelingScreenUiState()
        )
    }
//    init {
//        viewModelScope.launch {
//            refresh()
//        }
//    }
//
//    private suspend fun refresh(){
//        itemsRepository.getAllFuelings()
//            .combine(itemsRepository.getAllRoutes()) { fuelings, routes ->
//                fuelings.map { fueling ->
//                    val corespondingRoites = routes.filter { it.id_F == fueling.id_F }
//                    var distanceTraveled = 0f
//                    corespondingRoites.forEach { route -> distanceTraveled+=route.distance }
//
//                    val formatedDistance = DecimalFormat(
//                        "###,###", DecimalFormatSymbols(Locale.getDefault())
//                    ).format(distanceTraveled)
//
//                    fueling.toUi().copy(
//                        distance =  formatedDistance
//                    )
//                }
//            }
//            .collect { updatedFuelings ->
//                _fuelingScreenUiState.value = FuelingScreenUiState(updatedFuelings)
//            }
//    }

    companion object{
        private const val TIMEOUT_MILLIS = 5_000L
    }
}
data class FuelingScreenUiState(
    val itemList : List<FuelingAsUi> = listOf()
)

/*
 val _fuelingScreenUiState = MutableStateFlow(FuelingScreenUiState())
    val fuelingScreenUiState : StateFlow<FuelingScreenUiState> = _fuelingScreenUiState

    init {
        viewModelScope.launch {
            itemsRepository.getAllFuelings().filterNotNull().map {
                fuelingList -> fuelingList
            }
        }
    }
*/