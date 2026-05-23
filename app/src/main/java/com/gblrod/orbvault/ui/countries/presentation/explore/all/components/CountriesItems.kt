package com.gblrod.orbvault.ui.countries.presentation.explore.all.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.data.countries.remote.dto.CountriesDto
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.countries.presentation.home.components.OrbVaultSearchBar
import com.gblrod.orbvault.ui.shared.components.ScreenHeader
import com.gblrod.orbvault.ui.shared.components.country.CountryCard
import com.gblrod.orbvault.ui.shared.extensions.matchesQuery

@Composable
fun CountriesItems(
    exploreViewModel: ExploreViewModel,
    countryDetailsViewModel: CountryDetailsViewModel,
    countries: List<CountriesDto>,
    primaryValue: String,
    secondValue: String,
    colorCustom: Color,
    onClick: (CountriesDto) -> Unit
) {
    val favorites by countryDetailsViewModel.favorites.collectAsState()
    val searchQuery by exploreViewModel.searchQuery.collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current
    val focus = LocalFocusManager.current

    val filteredCountries = remember(key1 = countries, key2 = searchQuery) {
        countries.filter { country ->
            country.matchesQuery(searchQuery)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focus.clearFocus(force = true)
                        keyboardController?.hide()
                    }
                )
            }
    ) {
        item {
            ScreenHeader(
                primaryValue = primaryValue,
                secondValue = secondValue,
                colorCustom = colorCustom,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        item {
            OrbVaultSearchBar(
                query = searchQuery,
                onQueryChanged = { query ->
                    exploreViewModel.onSearchQueryChanged(query)
                },
                onClearSearch = { exploreViewModel.clearSearch() },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        items(
            items = filteredCountries,
            key = { it.cca3 }
        ) { country ->
            val isFavorite = favorites.any { it.code == country.cca3 }

            CountryCard(
                nameCommon = country.name.common,
                nameOfficial = country.name.official,
                flag = country.flags.png,
                isFavorite = isFavorite,
                onFavoriteClick = { countryDetailsViewModel.toggleFavorite(country) },
                onClick = {
                    countryDetailsViewModel.onCountrySelected(code = country.cca3)
                    onClick(country)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }
    }
}