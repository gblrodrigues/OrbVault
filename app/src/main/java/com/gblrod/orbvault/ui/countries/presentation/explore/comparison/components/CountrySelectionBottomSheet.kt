package com.gblrod.orbvault.ui.countries.presentation.explore.comparison.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.data.countries.remote.dto.CountriesDto
import com.gblrod.orbvault.ui.countries.presentation.explore.comparison.model.CardType
import com.gblrod.orbvault.ui.countries.presentation.home.components.OrbVaultSearchBar

@Composable
fun CountrySelectionBottomSheet(
    countries: List<CountriesDto>,
    onSelectedCountry: (CountriesDto) -> Unit,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onClearSearch: () -> Unit,
    primaryCountry: CountriesDto?,
    secondaryCountry: CountriesDto?,
    selectedCard: CardType?
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focus = LocalFocusManager.current

    val filteredCountries = remember(key1 = countries, key2 = searchQuery) {
        countries.filter { country ->
            country.name.common.contains(
                other = searchQuery,
                ignoreCase = true
            )
        }
    }

    OrbVaultSearchBar(
        query = searchQuery,
        onQueryChanged = onSearchQueryChanged,
        onClearSearch = onClearSearch,
        modifier = Modifier.padding(16.dp),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        }
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focus.clearFocus(force = true)
                        keyboardController?.hide()
                    }
                )
            },
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = filteredCountries,
            key = { country -> country.cca3 }
        ) { country ->
            val selectedCountry = when(selectedCard) {
                CardType.PRIMARY -> primaryCountry
                CardType.SECONDARY -> secondaryCountry
                null -> null
            }

            val blockedCountry = when (selectedCard) {
                CardType.PRIMARY -> secondaryCountry
                CardType.SECONDARY -> primaryCountry
                null -> null
            }

            val isSelected = selectedCountry?.cca3 == country.cca3
            val isDisabled = blockedCountry?.cca3 == country.cca3

            CountrySelectionItem(
                country = country,
                onClick = {
                    if (!isDisabled) {
                        onSelectedCountry(country)
                    }
                },
                onCountrySelected = isSelected,
                isDisabled = isDisabled
            )
        }
    }
}