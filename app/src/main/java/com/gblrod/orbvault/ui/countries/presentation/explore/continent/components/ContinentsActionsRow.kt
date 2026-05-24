package com.gblrod.orbvault.ui.countries.presentation.explore.continent.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.gblrod.orbvault.R
import com.gblrod.orbvault.navigation.Routes
import com.gblrod.orbvault.ui.countries.presentation.explore.continent.extensions.getErrorMessage
import com.gblrod.orbvault.ui.countries.presentation.explore.continent.model.ContinentItem
import com.gblrod.orbvault.ui.countries.presentation.explore.continent.model.ContinentUi
import com.gblrod.orbvault.ui.countries.presentation.explore.continent.state.ContinentUiState
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.shared.components.ErrorMessage
import com.gblrod.orbvault.ui.shared.components.LoadingScreen
import com.gblrod.orbvault.ui.theme.BlueActions
import com.gblrod.orbvault.ui.theme.GreenActions
import com.gblrod.orbvault.ui.theme.OrangeActions
import com.gblrod.orbvault.ui.theme.PinkActions
import com.gblrod.orbvault.ui.theme.RedActions

@Composable
fun ContinentsActionsRow(
    navHostController: NavHostController,
    exploreViewModel: ExploreViewModel,
    showDetailedError: Boolean = false
) {
    val uiState by exploreViewModel.continentState.collectAsState()

    when(val state = uiState) {
        is ContinentUiState.Loading -> {
            LoadingScreen()
        }

        is ContinentUiState.Error -> {
            ErrorMessage(
                message = state.getErrorMessage(showDetailedError = showDetailedError),
                onRetry = { exploreViewModel.allRetry() }
            )
        }

        is ContinentUiState.Success -> {
            val continents = listOf(
                ContinentUi(
                    image = painterResource(id = R.drawable.africa),
                    color = GreenActions,
                    region = "Africa",
                    label = R.string.continent_africa_label
                ),
                ContinentUi(
                    image = painterResource(id = R.drawable.americas),
                    color = RedActions,
                    region = "Americas",
                    label = R.string.continent_america_label
                ),
                ContinentUi(
                    image = painterResource(id = R.drawable.asia),
                    color = OrangeActions,
                    region = "Asia",
                    label = R.string.continent_asia_label
                ),
                ContinentUi(
                    image = painterResource(id = R.drawable.europe),
                    color = BlueActions,
                    region = "Europe",
                    label = R.string.continent_europa_label
                ),
                ContinentUi(
                    image = painterResource(id = R.drawable.oceania),
                    color = PinkActions,
                    region = "Oceania",
                    label = R.string.continent_oceania_label
                )
            )

            val items = continents.map { continent ->
                val count =  state.continents.firstOrNull{ it.continent == continent.region }
                    ?.totalCountries
                    ?: 0

                ContinentItem(
                    image = continent.image,
                    color = continent.color,
                    label = stringResource(id = R.string.continent_all_countries, count),
                    value = stringResource(id = continent.label),
                    onClick = {
                        navHostController.navigate(
                            Routes.Continent.createRoute(
                                region = continent.region
                            )
                        )
                    },
                )
            }
            ContinentsGrid(items = items)
        }
    }
}