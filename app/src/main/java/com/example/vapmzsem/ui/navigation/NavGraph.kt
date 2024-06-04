package com.example.vapmzsem.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.vapmzsem.ui.Fueling.FuelingAddScreen
import com.example.vapmzsem.ui.Fueling.FuelingAddScreenDestination
import com.example.vapmzsem.ui.Fueling.FuelingDetailDestination
import com.example.vapmzsem.ui.Fueling.FuelingDetailScreen
import com.example.vapmzsem.ui.Fueling.FuelingEditScreen
import com.example.vapmzsem.ui.Fueling.FuelingEditScreenDestination
import com.example.vapmzsem.ui.Fueling.FuelingScreen
import com.example.vapmzsem.ui.Fueling.FuelingScreenDestination
import com.example.vapmzsem.ui.MainScreen
import com.example.vapmzsem.ui.MainScreenDestination


@Composable
fun MyNavHost(
    navController : NavHostController,
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = MainScreenDestination.route,
        modifier = modifier
    ) {
        composable(route = MainScreenDestination.route){
            MainScreen(
                fuelingItemClicked = {navController.navigate("${FuelingDetailDestination.route}/${it}")},
                fuelingNewClicked = {navController.navigate(FuelingAddScreenDestination.route)}
            )
        }
        composable(route=FuelingScreenDestination.route){
            FuelingScreen(
                onNewFuelingClick = {navController.navigate(FuelingAddScreenDestination.route)},
                onItemClicked = {navController.navigate("${FuelingDetailDestination.route}/${it}")}
            )
        }
        composable(route = FuelingAddScreenDestination.route){
            FuelingAddScreen(
                onNavigateBack = {navController.popBackStack()},
                onNavigateUp = {navController.navigateUp()},
            )
        }
        composable(route = FuelingDetailDestination.routeWithArgs,
            arguments = listOf(navArgument(FuelingDetailDestination.fuelingIdArg){
                type = NavType.IntType
            })
        ){
            FuelingDetailScreen(onEditClick = {navController.navigate("${FuelingEditScreenDestination.route}/${it}")},
                navigateBack = {navController.popBackStack()},
                onNavigateUp = {navController.navigateUp()})
        }
        composable(route = FuelingEditScreenDestination.routeWithArgs,
        arguments = listOf(navArgument(FuelingEditScreenDestination.fuelingIdArg){
            type = NavType.IntType
        })
        ){
            FuelingEditScreen(navigateBack = { navController.popBackStack()}, onNavigateUp = {navController.navigateUp()})
        }

    }
}

interface NavigationDestination{
    val route: String
    val titleRes: Int
}