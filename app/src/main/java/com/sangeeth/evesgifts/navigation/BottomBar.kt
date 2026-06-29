package com.sangeeth.evesgifts.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(navController: NavController){
    val currentRoute = navController.currentBackStackEntryAsState()
        .value?.destination?.route

    NavigationBar {
        bottomNavItems.forEach { item->
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
                }
            )
        }
    }
}