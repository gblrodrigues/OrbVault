package com.gblrod.orbvault.ui.presentation.explore.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.gblrod.orbvault.R
import com.gblrod.orbvault.navigation.Routes
import com.gblrod.orbvault.ui.presentation.explore.components.CardExploreDetails
import com.gblrod.orbvault.ui.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.presentation.home.viewmodel.CountriesViewModel
import com.gblrod.orbvault.ui.theme.ExploreScreenSubTitleColor

@Composable
fun ExploreScreen(
    navHostController: NavHostController,
    exploreViewModel: ExploreViewModel,
    countriesViewModel: CountriesViewModel
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
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
        Spacer(modifier = Modifier.height(16.dp))

        CardExploreDetails(
            title = stringResource(id = R.string.explore_title_top10_populated_contries).uppercase(),
            background = R.drawable.populated_background,
            onClick = {
                navHostController.navigate(route = Routes.PopulatedCountries.route)
                exploreViewModel.fetchTopPopulatedCountries()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CardExploreDetails(
            title = stringResource(id = R.string.explore_title_top10_largest_contries).uppercase(),
            background = R.drawable.populated_background,
            onClick = {
                navHostController.navigate(route = Routes.LargestCountries.route)
                exploreViewModel.fetchTopLargestCountries()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CardExploreDetails(
            title = stringResource(id = R.string.explore_title_random_country).uppercase(),
            background = R.drawable.populated_background,
            onClick = {
                navHostController.navigate(route = Routes.RandomCountry.route)
                countriesViewModel.fetchRandomCountry()
            }
        )
    }
}