package com.example.vapmzsem.ui.Route

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.MutableStateFlow

interface RouteModifieInterface {

    fun updatedDistance(sdistance : String)
    fun updatedOdometer(sodometer: String)
}