package com.gblrod.orbvault.navigation

sealed class Routes(
    val route: String
) {
    object Home : Routes(
        route = "home"
    )

    object ScreenTest : Routes(
        route = "screentest"
    )
}