package com.gblrod.orbvault.ui.countries.presentation.explore.comparison.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.R
import com.gblrod.orbvault.data.countries.remote.dto.CountriesDto
import com.gblrod.orbvault.ui.countries.presentation.explore.comparison.components.ComparisonCountryCard
import com.gblrod.orbvault.ui.countries.presentation.explore.comparison.components.ComparisonResult
import com.gblrod.orbvault.ui.countries.presentation.explore.comparison.components.CountrySelectionBottomSheet
import com.gblrod.orbvault.ui.countries.presentation.explore.comparison.components.EmptyComparisonState
import com.gblrod.orbvault.ui.countries.presentation.explore.comparison.model.CardType
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.countries.presentation.state.ExploreUiState
import com.gblrod.orbvault.ui.shared.components.ErrorMessage
import com.gblrod.orbvault.ui.shared.components.LoadingScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComparisonScreen(
    exploreViewModel: ExploreViewModel
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val exploreUiState by exploreViewModel.allCountriesState.collectAsState()

    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedCard by remember { mutableStateOf<CardType?>(null) }
    var primaryCountry by remember { mutableStateOf<CountriesDto?>(null) }
    var secondaryCountry by remember { mutableStateOf<CountriesDto?>(null) }

    LaunchedEffect(Unit) {
        exploreViewModel.fetchAllCountries()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.country_comparison_description),
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ComparisonCountryCard(
                country = primaryCountry,
                onClick = {
                    selectedCard = CardType.PRIMARY
                    showBottomSheet = true
                }
            )

            Text(
                text = stringResource(id = R.string.country_comparison_vs),
                color = MaterialTheme.colorScheme.onSurface
            )

            ComparisonCountryCard(
                country = secondaryCountry,
                onClick = {
                    selectedCard = CardType.SECOND
                    showBottomSheet = true
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.country_comparison_label),
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(4.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
        ) {

            if (primaryCountry == null || secondaryCountry == null) {
                EmptyComparisonState()

            } else {
                ComparisonResult(
                    primaryCountry = primaryCountry!!,
                    secondaryCountry = secondaryCountry!!
                )
            }
        }
    }
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            scrimColor = Color.Transparent
        ) {
            when(val state = exploreUiState) {
                is ExploreUiState.Loading -> {
                    LoadingScreen()
                }

                is ExploreUiState.Success -> {
                    CountrySelectionBottomSheet(
                        countries = state.countries,
                        onSelectedCountry = { country ->
                            val isSameCountry = when (selectedCard) {
                                CardType.PRIMARY -> { secondaryCountry?.cca3 == country.cca3 }
                                CardType.SECOND -> { primaryCountry?.cca3 == country.cca3 }
                                null -> false
                            }

                            if (isSameCountry) {
                                return@CountrySelectionBottomSheet
                            }

                            when(selectedCard) {
                                CardType.PRIMARY -> { primaryCountry = country }
                                CardType.SECOND -> { secondaryCountry = country }
                                null -> Unit
                            }

                            showBottomSheet = false
                        }
                    )
                }

                is ExploreUiState.GlobalStatsSucess -> {}

                is ExploreUiState.Error -> {
                    val message = if (state.code == null) {
                        stringResource(id = state.messageResId)
                    } else {
                        stringResource(id = state.messageResId, state.code)
                    }

                    ErrorMessage(
                        message = message,
                        onRetry = { exploreViewModel.allRetry() }
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun ComparisonScreenPreview() {
//    ComparisonScreen()
//}