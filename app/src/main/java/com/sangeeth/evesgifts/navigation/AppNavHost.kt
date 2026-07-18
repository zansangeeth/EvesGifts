package com.sangeeth.evesgifts.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sangeeth.evesgifts.data.PriceViewModel
import com.sangeeth.evesgifts.ui.home.HomeScreen
import com.sangeeth.evesgifts.ui.login.LoginScreen
import com.sangeeth.evesgifts.ui.login.LoginViewModel
import com.sangeeth.evesgifts.ui.orders.OrdersScreen
import com.sangeeth.evesgifts.ui.orders.OrdersViewModel
import com.sangeeth.evesgifts.ui.profile.ProfileScreen
import com.sangeeth.evesgifts.ui.profile.ProfileViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: androidx.compose.ui.Modifier,
    priceViewModel: PriceViewModel = viewModel(),
    ordersViewModel: OrdersViewModel = viewModel(),
    profileViewModel: ProfileViewModel = viewModel(),
    loginViewModel: LoginViewModel = viewModel()
) {

    NavHost(
        navController = navController,
        startDestination = AppDestination.Login.route,
    ) {
        composable(AppDestination.Login.route) {
            LoginScreen(navController = navController, viewModel = loginViewModel)
        }
        composable(AppDestination.Home.route) {
            HomeScreen(priceViewModel)
        }
        composable(AppDestination.Orders.route) {
            OrdersScreen(ordersViewModel)
        }
        composable(AppDestination.Profile.route) {
            ProfileScreen(navController = navController, viewModel = profileViewModel)
        }
    }
}