package com.gblrod.orbvault.ui.countries.presentation.explore.news.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.gblrod.orbvault.navigation.Routes
import com.gblrod.orbvault.ui.countries.presentation.explore.news.screen.MoreNewsScreen
import com.gblrod.orbvault.ui.theme.ButtonNextRandomCountry

fun NavGraphBuilder.newsRoute(
    navHostController: NavHostController
) {
    composable(route = Routes.News.route) {
        MoreNewsScreen(
            navHostController = navHostController,
            colorCustom = ButtonNextRandomCountry
        )
    }
}