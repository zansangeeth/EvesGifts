package com.sangeeth.evesgifts.navigation

import com.sangeeth.evesgifts.R

data class BottomNavItem(
    val title: String,
    val route: String,
    val icon: Int
)
val bottomNavItems = listOf(

    BottomNavItem(
        title = "Home",
        route = "home",
        icon = R.drawable.ic_home
    ),
    BottomNavItem(
        title = "Orders",
        route = "orders",
        icon = R.drawable.ic_orders
    ),
    BottomNavItem(
        title = "Profile",
        route = "profile",
        icon = R.drawable.ic_profile
    ),
)