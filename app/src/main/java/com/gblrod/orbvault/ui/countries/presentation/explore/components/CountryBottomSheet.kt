package com.gblrod.orbvault.ui.countries.presentation.explore.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.R
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.countries.presentation.home.components.CardCountryDetails
import com.gblrod.orbvault.ui.countries.presentation.state.CountriesUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryBottomSheet(
    countryDetailsViewModel: CountryDetailsViewModel,
    onDismiss: () -> Unit,
    showBottomSheet: Boolean
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val countryState by countryDetailsViewModel.countryDetailsUiState.collectAsState()
    val bordersState by countryDetailsViewModel.bordersUiState.collectAsState()

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = sheetState,
            scrimColor = Color.Transparent
        ) {
            when (val state = countryState) {

                is CountriesUiState.Loading -> {
                    // LoadingScreen()
                }

                is CountriesUiState.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        CardCountryDetails(
                            country = state.country,
                            onFetchBorders = { country ->
                                countryDetailsViewModel.fetchBorders(country = country)
                            },
                            bordersState = bordersState,
                            onCountryClick = { code ->
                                countryDetailsViewModel.fetchCountryByCode(code = code)
                            },
                            countryQuery = {},
                            countryDetailsViewModel = countryDetailsViewModel
                        )
                    }
                }

                is CountriesUiState.Error -> {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.neighbors_error)
                        )
                    }
                }

                else -> {}
            }
        }
    }
}