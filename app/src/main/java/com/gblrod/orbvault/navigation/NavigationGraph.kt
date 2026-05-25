package com.gblrod.orbvault.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.gblrod.orbvault.navigation.graph.allCountriesRoute
import com.gblrod.orbvault.navigation.graph.exploreRoute
import com.gblrod.orbvault.navigation.graph.favoritesRoute
import com.gblrod.orbvault.navigation.graph.homeRoute
import com.gblrod.orbvault.navigation.graph.largestCountriesRoute
import com.gblrod.orbvault.navigation.graph.populatedCountriesRoute
import com.gblrod.orbvault.navigation.graph.randomCountryRoute
import com.gblrod.orbvault.navigation.graph.searchRoute
import com.gblrod.orbvault.navigation.graph.comparisonRoute
import com.gblrod.orbvault.ui.countries.presentation.explore.comparison.viewmodel.ComparisonViewModel
import com.gblrod.orbvault.navigation.graph.continentRoute
import com.gblrod.orbvault.navigation.graph.newsRoute
import com.gblrod.orbvault.navigation.graph.quizRoute
import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.viewmodel.QuizViewModel
import com.gblrod.orbvault.navigation.graph.statisticsRoute
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.countries.presentation.home.viewmodel.CountriesViewModel

@Composable
fun NavigationGraph(
    paddingValues: PaddingValues,
    navHostController: NavHostController,
    countriesViewModel: CountriesViewModel,
    exploreViewModel: ExploreViewModel,
    countryDetailsViewModel: CountryDetailsViewModel,
    quizViewModel: QuizViewModel,
    comparisonViewModel: ComparisonViewModel,
    snackbarHostState: SnackbarHostState
) {
    NavHost(
        navController = navHostController,
        startDestination = Routes.Home.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        homeRoute(
            navHostController = navHostController
        )

        exploreRoute(
            navHostController = navHostController,
            countryDetailsViewModel = countryDetailsViewModel,
            exploreViewModel = exploreViewModel
        )

        favoritesRoute(
            navHostController = navHostController,
            countryDetailsViewModel = countryDetailsViewModel,
            snackbarHostState = snackbarHostState
        )

        populatedCountriesRoute(
            countryDetailsViewModel = countryDetailsViewModel,
            exploreViewModel = exploreViewModel
        )

        largestCountriesRoute(
            countryDetailsViewModel = countryDetailsViewModel,
            exploreViewModel = exploreViewModel
        )

        allCountriesRoute(
            countryDetailsViewModel = countryDetailsViewModel,
            exploreViewModel = exploreViewModel
        )

        randomCountryRoute(
            countryDetailsViewModel = countryDetailsViewModel,
            countriesViewModel = countriesViewModel
        )

        searchRoute(
            navHostController = navHostController,
            countryDetailsViewModel = countryDetailsViewModel,
            countriesViewModel = countriesViewModel
        )

        statisticsRoute(
            exploreViewModel = exploreViewModel,
            countryDetailsViewModel = countryDetailsViewModel
        )

        newsRoute(navHostController = navHostController)

        quizRoute(
            navHostController = navHostController,
            quizViewModel = quizViewModel,
            snackbarHostState = snackbarHostState
        )

        comparisonRoute(
            exploreViewModel = exploreViewModel,
            comparisonViewModel = comparisonViewModel
        )

        continentRoute(
            exploreViewModel = exploreViewModel,
            countryDetailsViewModel = countryDetailsViewModel
        )
    }
}