package com.gblrod.orbvault.ui.presentation.explore.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gblrod.orbvault.R
import com.gblrod.orbvault.navigation.Routes
import com.gblrod.orbvault.ui.presentation.explore.components.CardExploreDetails
import com.gblrod.orbvault.ui.presentation.explore.viewmodel.ExploreViewModel

@Composable
fun ExploreScreen(
    navHostController: NavHostController,
    exploreViewModel: ExploreViewModel
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        CardExploreDetails(
            title = stringResource(id = R.string.explore_title_top10_populated_contries).uppercase(),
            background = R.drawable.populated_background,
            onClick = {
                navHostController.navigate(route = Routes.PopulatedCountries.route)
                exploreViewModel.fetchTopCountries()
            }
        )
    }
}