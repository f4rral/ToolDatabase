package com.example.tooldatabase.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tooldatabase.ui.screens.home.HomeScreen

@Composable
fun NavigationGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable(
            route = NavigationRoute.home
        ) {
            HomeScreen()
        }

        composable(
            route = NavigationRoute.contactCreate
        ) {
//            ContactCreateScreen()
        }

        composable(
            route = "${NavigationRoute.contactDetail}/{contactId}",
            arguments = listOf(
                navArgument("contactId") {
                    type = NavType.IntType
                }
            )
        ) { stackEntry ->
            val contactId = stackEntry.arguments?.getInt("contactId")

            if (contactId != null) {
//                ContactDetailScreen(contactId)
            }
        }
    }
}

object NavigationRoute {
    const val home = "HOME"
    const val contactCreate = "CONTACT_CREATE"
    const val contactDetail =  "CONTACT_DETAIL"
}