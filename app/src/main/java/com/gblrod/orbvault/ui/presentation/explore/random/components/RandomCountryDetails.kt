package com.gblrod.orbvault.ui.presentation.explore.random.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gblrod.orbvault.R
import com.gblrod.orbvault.data.dto.CountriesDto
import com.gblrod.orbvault.ui.presentation.home.components.CardCountryDetails
import com.gblrod.orbvault.ui.presentation.home.viewmodel.CountriesViewModel
import com.gblrod.orbvault.ui.theme.CountryRandomColor

@Composable
fun RandomCountryDetails(
    countriesViewModel: CountriesViewModel,
    country: CountriesDto,
    onCountryClick: (String) -> Unit,
    countryQuery: (String) -> Unit
) {
    val bordersState by countriesViewModel.bordersUiState.collectAsState()

    Column {
        Text(
            text = stringResource(id = R.string.random_country_label_explore),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = stringResource(id = R.string.random_country_label),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = CountryRandomColor
        )
    }
    Spacer(modifier = Modifier.height(16.dp))

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CardCountryDetails(
            country = country,
            onFetchBorders = {
                countriesViewModel.fetchBorders(country = it)
            },
            bordersState = bordersState,
            onCountryClick = onCountryClick,
            countryQuery = countryQuery
        )

        NextCountryRandom(
            onClick = {
                countriesViewModel.fetchRandomCountry()
            }
        )
    }
}