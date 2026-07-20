package com.sangeeth.evesgifts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.sangeeth.evesgifts.navigation.AppDestination
import com.sangeeth.evesgifts.navigation.AppNavHost
import com.sangeeth.evesgifts.navigation.BottomBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val showBottomBar = currentRoute != AppDestination.Login.route

            val isLoggedIn = FirebaseAuth.getInstance().currentUser != null

            val startDestination = if (isLoggedIn) {
                AppDestination.Home.route
            }else{
                AppDestination.Login.route
            }

            Scaffold(
                bottomBar = {
                    if (showBottomBar){
                        BottomBar(navController)
                    }
                },

                ) { innerPadding ->
                AppNavHost(navController, modifier = Modifier.padding(innerPadding), startDestination = startDestination)
            }
        }
    }
}

