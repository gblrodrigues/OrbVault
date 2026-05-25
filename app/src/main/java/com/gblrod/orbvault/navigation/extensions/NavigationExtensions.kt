package com.gblrod.orbvault.navigation.extensions

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

fun NavHostController.navigateToBottomBar(route: String) {
    navigate(route) {
        launchSingleTop = true
        restoreState = true

        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
    }
}