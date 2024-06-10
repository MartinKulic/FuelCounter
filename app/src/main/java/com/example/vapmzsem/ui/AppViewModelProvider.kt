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
import com.example.vapmzsem.ui.Home.HomeScreen
import com.example.vapmzsem.ui.Home.HomeViewModel
import com.example.vapmzsem.ui.Route.RouteAddViewModel
import com.example.vapmzsem.ui.Route.RouteDetailViewModel
import com.example.vapmzsem.ui.Route.RouteEditViewModel
import com.example.vapmzsem.ui.Route.RouteViewModel
import com.example.vapmzsem.ui.Statistics.StatisticScreen
import com.example.vapmzsem.ui.Statistics.StatisticViewModel

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
                repository = myApplication().container.appRepository,
                averageFuelConsumption = myApplication().container.average_fuel_consumption
            )
        }
        //RouteAdd
        initializer {
            RouteAddViewModel(
                repository = myApplication().container.appRepository
            )
        }
        //RoudeDetail
        initializer {
            RouteDetailViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                repository = myApplication().container.appRepository,
                averageFuelConsumption = myApplication().container.average_fuel_consumption
            )
        }
        //RouteEdit
        initializer {
            RouteEditViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                repository = myApplication().container.appRepository
            )
        }

        //Homescreen
        initializer {
            HomeViewModel(
                repository = myApplication().container.appRepository,
                averageFuelConsumption = myApplication().container.average_fuel_consumption
            )
        }
        //StatisticScreen
        initializer {
            StatisticViewModel(
                repository = myApplication().container.appRepository,
                averageFuelConsumption = myApplication().container.average_fuel_consumption
                )
        }
    }
}

fun CreationExtras.myApplication () : MyApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)