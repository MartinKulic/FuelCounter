package com.example.vapmzsem.ui.Home


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.vapmzsem.data.AppRepository
import java.util.Date

class FuelingAddViewModel(
    private val repository: AppRepository
) : ViewModel() {
    var fuelingUiState by mutableStateOf(FuelingUiState())
        private set

    fun updateUiState (fueligDetail: FuelingDetail){
        fuelingUiState = FuelingUiState(isEntryValid = validateInput(fueligDetail), details = fueligDetail)
    }
    fun validateInput(details : FuelingDetail = fuelingUiState.details) : Boolean{
        return with(details){
            quantity.isNotBlank() && total_price.isNotBlank() && odometter >= "0"
        }
    }
}

data class FuelingUiState(
    val isEntryValid : Boolean = false,
    val details: FuelingDetail = FuelingDetail()

)
data class FuelingDetail(
    val quantity : String = "",
    val total_price : String = "",
    val full_tank : Boolean = false,
    val fuel_type : String = "",
    val fueling_Station : String = "",
    val time : Date? = Date(),
    val odometter: String = "0"
)