package com.gblrod.orbvault.navigation

import com.gblrod.orbvault.R

fun mapRouteToNavigationUiState(
    route: String?
): NavigationUiState {

    val principalScreens = setOf(
        Routes.Home.route,
        Routes.Explore.route,
        Routes.Favorites.route
    )

    val isPrincipalScreen = route in principalScreens
    val showBackButton = !isPrincipalScreen

    if (route == Routes.Search.route) {
        return NavigationUiState(
            titleRes = R.string.app_name,
            showTopBar = false,
            showBottomBar = false,
            showDrawerIcon = false,
            showBackButton = false
        )
    }

    val titleRes = when (route) {
        Routes.Home.route -> R.string.topbar_home_title
        Routes.News.route -> R.string.topbar_news_title
        Routes.Quiz.route -> R.string.topbar_quiz_title
        Routes.Explore.route -> R.string.topbar_explore_title
        Routes.Favorites.route -> R.string.topbar_favorite_title
        Routes.StatisticsList.route -> R.string.topbar_statistics_title
        Routes.AllCountries.route -> R.string.explore_title_all_countries
        Routes.RandomCountry.route -> R.string.topbar_random_country_title
        Routes.LargestCountries.route -> R.string.topbar_largest_countries_title
        Routes.PopulatedCountries.route -> R.string.topbar_populated_countries_title
        else -> R.string.topbar_principal_title
    }

    return NavigationUiState(
        showTopBar = route != null,
        showBackButton = showBackButton,
        showDrawerIcon = isPrincipalScreen,
        showBottomBar = isPrincipalScreen,
        titleRes = titleRes
    )
}