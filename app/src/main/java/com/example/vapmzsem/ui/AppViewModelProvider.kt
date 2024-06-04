package com.example.vapmzsem.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.vapmzsem.MyApplication
import com.example.vapmzsem.ui.Home.FuelinViewModel
import com.example.vapmzsem.ui.Home.FuelingAddViewModel
import com.example.vapmzsem.ui.Home.FuelingDetailViewModel
import com.example.vapmzsem.ui.Home.FuelingEditViewModel

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
    }
}

fun CreationExtras.myApplication () : MyApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)