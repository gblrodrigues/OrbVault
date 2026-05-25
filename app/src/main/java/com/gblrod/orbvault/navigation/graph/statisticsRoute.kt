package com.gblrod.orbvault.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gblrod.orbvault.navigation.Routes
import com.gblrod.orbvault.ui.countries.presentation.explore.statistics.screen.MoreStatisticsScreen
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.theme.ButtonNextRandomCountry

fun NavGraphBuilder.statisticsRoute(
    exploreViewModel: ExploreViewModel,
    countryDetailsViewModel: CountryDetailsViewModel
) {
    composable(route = Routes.StatisticsList.route) {
        MoreStatisticsScreen(
            exploreViewModel = exploreViewModel,
            colorCustom = ButtonNextRandomCountry,
            countryDetailsViewModel = countryDetailsViewModel
        )
    }
}