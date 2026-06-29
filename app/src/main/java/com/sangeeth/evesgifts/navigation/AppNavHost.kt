package com.sangeeth.evesgifts.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sangeeth.evesgifts.ui.home.HomeScreen
import com.sangeeth.evesgifts.ui.orders.OrdersScreen
import com.sangeeth.evesgifts.ui.profile.ProfileScreen

@Composable
fun AppNavHost(
    navController: NavHostController
){

    NavHost(
        navController = navController,
        startDestination = AppDestination.Home.route
    ){
        composable(AppDestination.Home.route){
            HomeScreen()
        }
        composable(AppDestination.Orders.route){
            OrdersScreen()
        }
        composable(AppDestination.Profile.route){
            ProfileScreen()
        }
    }
}