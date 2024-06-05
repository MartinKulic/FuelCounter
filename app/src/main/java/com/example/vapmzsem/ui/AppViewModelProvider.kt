package com.example.vapmzsem.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.vapmzsem.MyApplication
import com.example.vapmzsem.ui.Fueling.FuelinViewModel
import com.example.vapmzsem.ui.Fueling.FuelingAddViewModel
import com.example.vapmzsem.ui.Fueling.FuelingDetailViewModel
import com.example.vapmzsem.ui.Fueling.FuelingEditViewModel
import com.example.vapmzsem.ui.Route.RouteAddViewModel
import com.example.vapmzsem.ui.Route.RouteScreen
import com.example.vapmzsem.ui.Route.RouteViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
       // Initializer for FuelingScreenViewModel
        initializer {
            FuelinViewModel(
                myApplication().container.appRepository
            )
        }
        // Initializer for FuelingAdd
        initializer {
            FuelingAddViewModel(myApplication().container.appRepository)
        }
        // FuelongDetail
        initializer {
            FuelingDetailViewModel(
                this.createSavedStateHandle(),
                myApplication().container.appRepository
            )
        }
        //FuelingEdit
        initializer {
            FuelingEditViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                repository = myApplication().container.appRepository
            )
        }


        //RouteScreen
        initializer {
            RouteViewModel(
                repository = myApplication().container.appRepository
            )
        }
        //RouteAff
        initializer {
            RouteAddViewModel(
                repository = myApplication().container.appRepository
            )
        }
    }
}

fun CreationExtras.myApplication () : MyApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)