package com.gblrod.orbvault.ui.countries.presentation.explore.all.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.data.countries.remote.dto.CountriesDto
import com.gblrod.orbvault.ui.countries.presentation.explore.components.PaginationLoading
import com.gblrod.orbvault.ui.shared.components.ScreenHeader
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.countries.presentation.state.ExploreUiState
import com.gblrod.orbvault.ui.shared.components.country.CountryCard
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AllCountriesItems(
    exploreViewModel: ExploreViewModel,
    countryDetailsViewModel: CountryDetailsViewModel,
    primaryValue: String,
    secondValue: String,
    colorCustom: Color,
    onClick: (CountriesDto) -> Unit
) {
    val uiState by exploreViewModel.allCountriesState.collectAsState()
    val countries = (uiState as? ExploreUiState.Success)?.countries ?: emptyList()
    val favorites by countryDetailsViewModel.favorites.collectAsState()
    val isLoadingMore by exploreViewModel.isLoadingMore.collectAsState()

    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow {
            val lastVisibleItem =
                listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

            val totalItems = listState.layoutInfo.totalItemsCount

            lastVisibleItem >= totalItems - 3
        }.collectLatest { shouldLoadMore ->
            if (shouldLoadMore) {
                exploreViewModel.loadMoreCountries()
            }
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item {
            ScreenHeader(
                primaryValue = primaryValue,
                secondValue = secondValue,
                colorCustom = colorCustom,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        items(
            items = countries,
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
        if (isLoadingMore) {
            item {
                PaginationLoading()
            }
        }
    }
}