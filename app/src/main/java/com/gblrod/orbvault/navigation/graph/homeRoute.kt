package com.gblrod.orbvault.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.gblrod.orbvault.navigation.Routes
import com.gblrod.orbvault.ui.countries.presentation.home.screen.HomeScreen

fun NavGraphBuilder.homeRoute(
    navHostController: NavHostController
) {
    composable(route = Routes.Home.route) {
        HomeScreen(
            onClick = { navHostController.navigate(Routes.Search.route) }
        )
    }
}