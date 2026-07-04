package com.sangeeth.evesgifts.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sangeeth.evesgifts.R.color

@Composable
fun BottomBar(navController: NavController){
    val currentRoute = navController.currentBackStackEntryAsState()
        .value?.destination?.route

    NavigationBar(
        containerColor = colorResource(color.primary_color)
    ) {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(painter = painterResource(item.icon), contentDescription = item.title)
                },
                label = {
                    Text(item.title)
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    selectedIndicatorColor = Color.Gray,
                    unselectedIconColor = Color.White,
                    unselectedTextColor = Color.White,
                    disabledIconColor = Color.White,
                    disabledTextColor = Color.White
                )
            )
        }
    }
}