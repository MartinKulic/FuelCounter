package com.example.vapmzsem.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vapmzsem.ui.Home.FuelingScreen
import com.example.vapmzsem.ui.Home.FuelingScreenDestination


@Composable
fun NavHost(
    navController : NavHostController,
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = FuelingScreenDestination.route,
        modifier = modifier
    ) {
        composable(route=FuelingScreenDestination.route){
            FuelingScreen()
        }
    }
}

interface NavigationDestination{
    val route: String
    val titleRes: Int
}