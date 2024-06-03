package com.example.vapmzsem

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.vapmzsem.ui.navigation.NavHost

@Composable
fun MyApp(navController: NavHostController = rememberNavController()){
    NavHost(navController = navController)
}