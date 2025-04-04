package com.example.tooldatabase.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tooldatabase.screens.tool_body.detail.ToolBodyDetailScreen
import com.example.tooldatabase.screens.tool_body.ToolBodyScreen

@Composable
fun NavigationGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "TOOL_BODY_LIST"
    ) {
        composable(
            route = NavigationRoute.TOOL_BODY_LIST
        ) {
            ToolBodyScreen()
        }

        composable(
            route = "${NavigationRoute.TOOL_BODY_DETAIL}/{toolBodyId}",
            arguments = listOf(
                navArgument("toolBodyId") {
                    type = NavType.IntType
                }
            )
        ) { stackEntry ->
            val toolBodyId = stackEntry.arguments?.getInt("toolBodyId")

            if (toolBodyId != null) {
                ToolBodyDetailScreen(toolBodyId = toolBodyId)
            }

        }
    }
}

object NavigationRoute {
    const val TOOL_BODY_LIST = "TOOL_BODY_LIST"
    const val TOOL_BODY_DETAIL =  "TOOL_BODY_DETAIL"
}