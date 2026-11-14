package com.example.homework10.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.homework10.ui.catalog.CatalogScreen
import com.example.homework10.ui.details.AppDetailsScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "catalog"
    ) {
        composable("catalog") {
            CatalogScreen(
                onAppClick = { appId ->
                    navController.navigate("details/$appId")
                }
            )
        }

        composable(
            route = "details/{appId}",
            arguments = listOf(navArgument("appId") { type = NavType.StringType })
        ) {
            AppDetailsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}