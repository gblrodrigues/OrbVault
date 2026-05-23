package com.gblrod.orbvault.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gblrod.orbvault.ui.countries.presentation.explore.all.screen.AllCountriesScreen
import com.gblrod.orbvault.ui.countries.presentation.explore.comparison.navigation.comparisonRoute
import com.gblrod.orbvault.ui.countries.presentation.explore.comparison.viewmodel.ComparisonViewModel
import com.gblrod.orbvault.ui.countries.presentation.explore.continent.navigation.continentRoute
import com.gblrod.orbvault.ui.countries.presentation.explore.largest.screen.LargestCountriesScreen
import com.gblrod.orbvault.ui.countries.presentation.explore.news.navigation.newsRoute
import com.gblrod.orbvault.ui.countries.presentation.explore.populated.screen.PopulatedCountriesScreen
import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.navigation.quizRoute
import com.gblrod.orbvault.ui.countries.presentation.explore.quiz.viewmodel.QuizViewModel
import com.gblrod.orbvault.ui.countries.presentation.explore.random.screen.RandomCountryScreen
import com.gblrod.orbvault.ui.countries.presentation.explore.screen.ExploreScreen
import com.gblrod.orbvault.ui.countries.presentation.explore.statistics.navigation.statisticsRoute
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.countries.presentation.favorites.screen.FavoritesScreen
import com.gblrod.orbvault.ui.countries.presentation.home.components.SearchScreen
import com.gblrod.orbvault.ui.countries.presentation.home.screen.HomeScreen
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
        composable(route = Routes.Home.route) {
            HomeScreen(
                onClick = { navHostController.navigate(Routes.Search.route) }
            )
        }

        composable(route = Routes.Explore.route) {
            ExploreScreen(
                navHostController = navHostController,
                exploreViewModel = exploreViewModel,
                countryDetailsViewModel = countryDetailsViewModel
            )
        }

        composable(route = Routes.Favorites.route) {
            FavoritesScreen(
                countryDetailsViewModel = countryDetailsViewModel,
                snackbarHostState = snackbarHostState,
                onNavigateExplore = {
                    navHostController.navigateToBottomBar(route = Routes.Explore.route)
                }
            )
        }

        composable(route = Routes.PopulatedCountries.route) {
            PopulatedCountriesScreen(
                exploreViewModel = exploreViewModel,
                countryDetailsViewModel = countryDetailsViewModel
            )
        }

        composable(route = Routes.LargestCountries.route) {
            LargestCountriesScreen(
                exploreViewModel = exploreViewModel,
                countryDetailsViewModel = countryDetailsViewModel
            )
        }

        composable(route = Routes.AllCountries.route) {
            AllCountriesScreen(
                exploreViewModel = exploreViewModel,
                countryDetailsViewModel = countryDetailsViewModel
            )
        }

        composable(route = Routes.RandomCountry.route) {
            RandomCountryScreen(
                countriesViewModel = countriesViewModel,
                countryDetailsViewModel = countryDetailsViewModel
            )
        }

        composable(route = Routes.Search.route) {
            SearchScreen(
                countryDetailsViewModel = countryDetailsViewModel,
                countriesViewModel = countriesViewModel,
                onBack = { navHostController.popBackStack() }
            )
        }

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