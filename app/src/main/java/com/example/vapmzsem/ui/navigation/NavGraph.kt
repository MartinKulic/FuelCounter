package com.example.vapmzsem.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.vapmzsem.ui.Home.FuelingAddScreen
import com.example.vapmzsem.ui.Home.FuelingAddScreenDestination
import com.example.vapmzsem.ui.Home.FuelingDetailDestination
import com.example.vapmzsem.ui.Home.FuelingDetailScreen
import com.example.vapmzsem.ui.Home.FuelingScreen
import com.example.vapmzsem.ui.Home.FuelingScreenDestination


@Composable
fun MyNavHost(
    navController : NavHostController,
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = FuelingScreenDestination.route,
        modifier = modifier
    ) {
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
            FuelingDetailScreen(onEditClick = {}, navigateBack = {navController.popBackStack()})

        }

    }
}

interface NavigationDestination{
    val route: String
    val titleRes: Int
}