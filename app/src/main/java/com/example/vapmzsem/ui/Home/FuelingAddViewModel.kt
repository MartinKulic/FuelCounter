package com.example.vapmzsem.ui.Home


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.vapmzsem.data.AppRepository
import com.example.vapmzsem.data.Fueling
import java.util.Calendar
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
    val details: FuelingDetail = FuelingDetail()

)
data class FuelingDetail(
    val quantity : String = "",
    val total_price : String = "",
    val full_tank : Boolean = false,
    val fuel_type : String = "",
    val fueling_Station : String = "",
    val time : Calendar = Calendar.getInstance(),
    val odometter: String = "0"
){
    fun toFueling() : Fueling {
        return Fueling(
            quantity = quantity.toFloatOrNull() ?: 1f,
            total_price = total_price.toFloatOrNull() ?: 1f,
            full_tank = full_tank,
            fuel_type = fuel_type,
            fueling_Station = fueling_Station,
            time = time.time,
            odometter = odometter.toIntOrNull() ?: 0
        )
    }
}