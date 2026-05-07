package com.gblrod.orbvault.ui.countries.presentation.explore.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gblrod.orbvault.R
import com.gblrod.orbvault.navigation.Routes
import com.gblrod.orbvault.ui.countries.presentation.explore.components.CardExploreDetails
import com.gblrod.orbvault.ui.countries.presentation.explore.components.CategoryItemsHeader
import com.gblrod.orbvault.ui.shared.components.ScreenHeader
import com.gblrod.orbvault.ui.countries.presentation.explore.news.components.ExploreActionsRow
import com.gblrod.orbvault.ui.countries.presentation.explore.statistics.screen.StatisticsScreen
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.theme.ExploreScreenSubTitleColor

@Composable
fun ExploreScreen(
    navHostController: NavHostController,
    exploreViewModel: ExploreViewModel,
    countryDetailsViewModel: CountryDetailsViewModel
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            ScreenHeader(
                primaryValue = stringResource(id = R.string.explore_screen_title),
                secondValue = stringResource(id = R.string.explore_screen_sub_title),
                colorCustom = ExploreScreenSubTitleColor,
            )
        }

        item {
            CardExploreDetails(
                title = stringResource(id = R.string.explore_title_top10_populated_contries).uppercase(),
                background = R.drawable.explore_item_background,
                onClick = {
                    navHostController.navigate(route = Routes.PopulatedCountries.route)
                }
            )
        }

        item {
            CardExploreDetails(
                title = stringResource(id = R.string.explore_title_top10_largest_contries).uppercase(),
                background = R.drawable.explore_item_background,
                onClick = {
                    navHostController.navigate(route = Routes.LargestCountries.route)
                }
            )
        }

        item {
            CardExploreDetails(
                title = stringResource(id = R.string.explore_title_all_countries).uppercase(),
                background = R.drawable.explore_item_background,
                onClick = {
                    navHostController.navigate(route = Routes.AllCountries.route)
                }
            )
        }

        item {
            CardExploreDetails(
                title = stringResource(id = R.string.explore_title_random_country).uppercase(),
                background = R.drawable.explore_item_background,
                onClick = {
                    navHostController.navigate(route = Routes.RandomCountry.route)
                }
            )
        }

        item {
            CategoryItemsHeader(
                title = R.string.explore_news_title,
                showAllItems = R.string.explore_show_all_items,
                onClick = { navHostController.navigate(route = Routes.News.route) }
            )
        }

        item {
            ExploreActionsRow(
                navHostController = navHostController
            )
        }

        item {
            CategoryItemsHeader(
                title = R.string.explore_statistics_title,
                showAllItems = R.string.explore_show_all_items,
                onClick = { navHostController.navigate(route = Routes.StatisticsList.route) }
            )
        }

        item {
            StatisticsScreen(
                exploreViewModel = exploreViewModel,
                countryDetailsViewModel = countryDetailsViewModel,
                isCompact = true
            )
        }
    }
}