package com.gblrod.orbvault.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gblrod.orbvault.presentation.screen.OrbVaultScreen
import com.gblrod.orbvault.presentation.screen.ScreenTest
import com.gblrod.orbvault.presentation.viewmodel.CountriesViewModel

@Composable
fun NavigationGraph(
    paddingValues: PaddingValues,
    navHostController: NavHostController,
    countriesViewModel: CountriesViewModel
) {
    NavHost(
        navController = navHostController,
        startDestination = Routes.Home.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        composable(route = Routes.Home.route) {
            OrbVaultScreen(countriesViewModel = countriesViewModel)
        }

        composable(route = Routes.ScreenTest.route) {
            ScreenTest()
        }
    }
}