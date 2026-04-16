package com.gblrod.orbvault.navigation

sealed class Routes(
    val route: String
) {
    object Home : Routes(
        route = "home"
    )

    object Favorites : Routes(
        route = "favorites"
    )

    object Explore : Routes(
        route = "explore"
    )
}