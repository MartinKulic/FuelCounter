package com.example.vapmzsem.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.vapmzsem.MyApplication
import com.example.vapmzsem.ui.Home.FuelinViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
       // Initializer for FuelingScreenViewModel
        initializer {
            FuelinViewModel(
                myApplication().container.appRepository
            )
        }
    }
}

fun CreationExtras.myApplication () : MyApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)