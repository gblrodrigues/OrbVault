package com.gblrod.orbvault.ui.countries.presentation.explore.comparison.state

import com.gblrod.orbvault.data.countries.remote.dto.CountriesDto
import com.gblrod.orbvault.ui.countries.presentation.explore.comparison.model.CardType

data class ComparisonUiState(
    val primaryCountry: CountriesDto? = null,
    val secondaryCountry: CountriesDto? = null,
    val selectedCard: CardType? = null,
    val showBottomSheet: Boolean = false,
    val searchQuery: String = ""
)