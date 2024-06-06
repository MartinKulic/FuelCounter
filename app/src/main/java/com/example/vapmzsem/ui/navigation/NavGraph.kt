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
import com.example.vapmzsem.ui.Route.RouteAddScreen
import com.example.vapmzsem.ui.Route.RouteAddScreenDestination
import com.example.vapmzsem.ui.Route.RouteDetailDestinantion
import com.example.vapmzsem.ui.Route.RouteDetailScreen
import com.example.vapmzsem.ui.Route.RouteEditDestination
import com.example.vapmzsem.ui.Route.RouteEditScreen


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
                fuelingNewClicked = {navController.navigate(FuelingAddScreenDestination.route)},
                routeItemClicked = {navController.navigate("${RouteDetailDestinantion.route}/${it}")},
                routeNewClicked = {navController.navigate(RouteAddScreenDestination.route)},
            )
        }
        composable(route=FuelingScreenDestination.route){
            FuelingScreen(
                onNewFuelingClick = {navController.navigate(FuelingAddScreenDestination.route)},
                onItemClicked = {navController.navigate("${FuelingDetailDestination.route}/${it}")},

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
            FuelingEditScreen(
                navigateBack = { navController.popBackStack()},
                onNavigateUp = {navController.navigateUp()},
                onModificationConfirm = {
                    navController.popBackStack(MainScreenDestination.route, inclusive = false)
                }
            )
        }
        composable(route = RouteAddScreenDestination.route){
            RouteAddScreen(
                onNavigateBack = {navController.popBackStack()},
                onNavigateUp = {navController.navigateUp()},
            )
        }
        composable(route = RouteDetailDestinantion.routeWithArgs,
            arguments = listOf(navArgument(RouteDetailDestinantion.routeIdArg){
                type = NavType.IntType
            })
        ){
            RouteDetailScreen(navigateBack = { navController.popBackStack() },
                onNavigateUp = {navController.navigateUp()},
                onEditClick = {navController.navigate("${RouteEditDestination.route}/${it}")}
            )
        }
        composable(route = RouteEditDestination.routeWithArgs,
            arguments = listOf(navArgument(RouteDetailDestinantion.routeIdArg){
                type = NavType.IntType
            })
        ){
            RouteEditScreen(
                navigateBack = {navController.popBackStack()},
                navigateUp = {navController.navigateUp()},
                onModificationConfirm = {
                    navController.popBackStack(
                        MainScreenDestination.route,
                        inclusive = false
                    )
                }
            )
        }

    }
}

interface NavigationDestination{
    val route: String
    val titleRes: Int
}