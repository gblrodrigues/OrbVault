package com.gblrod.orbvault.ui.countries.presentation.explore.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.gblrod.orbvault.R
import com.gblrod.orbvault.navigation.Routes
import com.gblrod.orbvault.ui.countries.presentation.explore.components.CardExploreDetails
import com.gblrod.orbvault.ui.countries.presentation.explore.news.components.ExploreActionsRow
import com.gblrod.orbvault.ui.countries.presentation.explore.statistics.screen.StatisticsScreen
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.countries.presentation.home.viewmodel.CountriesViewModel
import com.gblrod.orbvault.ui.theme.ExploreScreenSubTitleColor
import com.gblrod.orbvault.ui.theme.LargestCountriesColor

@Composable
fun ExploreScreen(
    navHostController: NavHostController,
    exploreViewModel: ExploreViewModel,
    countriesViewModel: CountriesViewModel,
    countryDetailsViewModel: CountryDetailsViewModel
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Column {
                Text(
                    text = stringResource(id = R.string.explore_screen_title),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = stringResource(id = R.string.explore_screen_sub_title),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = ExploreScreenSubTitleColor
                )
            }
        }

        item {
            CardExploreDetails(
                title = stringResource(id = R.string.explore_title_top10_populated_contries).uppercase(),
                background = R.drawable.populated_background,
                onClick = {
                    navHostController.navigate(route = Routes.PopulatedCountries.route)
                    exploreViewModel.fetchTopPopulatedCountries()
                }
            )
        }

        item {
            CardExploreDetails(
                title = stringResource(id = R.string.explore_title_top10_largest_contries).uppercase(),
                background = R.drawable.populated_background,
                onClick = {
                    navHostController.navigate(route = Routes.LargestCountries.route)
                    exploreViewModel.fetchTopLargestCountries()
                }
            )
        }

        item {
            CardExploreDetails(
                title = stringResource(id = R.string.explore_title_random_country).uppercase(),
                background = R.drawable.populated_background,
                onClick = {
                    navHostController.navigate(route = Routes.RandomCountry.route)
                    countriesViewModel.fetchRandomCountry()
                }
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.explore_news_title),
                    style = MaterialTheme.typography.titleMedium
                )
                TextButton(
                    onClick = {
                        navHostController.navigate(route = Routes.News.route)
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.explore_news_more_infos),
                        style = MaterialTheme.typography.titleSmall,
                        color = LargestCountriesColor
                    )
                }
            }
        }

        item {
            ExploreActionsRow(
                navHostController = navHostController
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.explore_statistics_title),
                    style = MaterialTheme.typography.titleMedium
                )
                TextButton(
                    onClick = {
                        navHostController.navigate(route = Routes.StatisticsList.route)
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.explore_statistics_more_infos),
                        style = MaterialTheme.typography.titleSmall,
                        color = LargestCountriesColor
                    )
                }
            }
        }

        item {
            StatisticsScreen(
                exploreViewModel = exploreViewModel,
                countryDetailsViewModel = countryDetailsViewModel
            )
        }
    }
}