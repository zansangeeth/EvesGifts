package com.sangeeth.evesgifts.navigation

import com.sangeeth.evesgifts.R

sealed class AppDestination (val route: String){
    object Login : AppDestination("login")
    object Home : AppDestination("home")
    object Orders : AppDestination("orders")
    object Profile : AppDestination("profile")
}
