package com.gblrod.orbvault.ui.countries.presentation.explore.comparison.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gblrod.orbvault.data.countries.remote.dto.CountriesDto
import com.gblrod.orbvault.data.countries.repository.CountriesRepository
import com.gblrod.orbvault.ui.countries.presentation.explore.comparison.model.CardType
import com.gblrod.orbvault.ui.countries.presentation.explore.comparison.state.ComparisonUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ComparisonViewModel(
    private val repository: CountriesRepository
) : ViewModel() {
    private val _comparisonState = MutableStateFlow(ComparisonUiState())
    val comparisonState: StateFlow<ComparisonUiState> = _comparisonState

    fun onCardSelected(cardType: CardType) {
        _comparisonState.update {
            it.copy(
                selectedCard = cardType,
                showBottomSheet = true
            )
        }
    }

    fun selectCountry(country: CountriesDto) {
        val currentState = _comparisonState.value

        val isSameCountry = when (currentState.selectedCard) {
            CardType.PRIMARY -> currentState.secondaryCountry?.cca3 == country.cca3
            CardType.SECONDARY -> currentState.primaryCountry?.cca3 == country.cca3
            null -> false
        }

        if (isSameCountry) return

        viewModelScope.launch {
            val fullCountry = repository.fetchCountryByCode(country.cca3)
            _comparisonState.update {
                when (it.selectedCard) {
                    CardType.PRIMARY -> {
                        it.copy(
                            primaryCountry = fullCountry,
                            showBottomSheet = false,
                            searchQuery = ""
                        )
                    }

                    CardType.SECONDARY -> {
                        it.copy(
                            secondaryCountry = fullCountry,
                            showBottomSheet = false,
                            searchQuery = ""
                        )
                    }

                    null -> it
                }
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _comparisonState.update {
            it.copy(searchQuery = query)
        }
    }

    fun clearSearch() {
        _comparisonState.update {
            it.copy(
                searchQuery = ""
            )
        }
    }

    fun dismissBottomSheet() {
        _comparisonState.update {
            it.copy(
                showBottomSheet = false,
                searchQuery = ""
            )
        }
    }

    fun removeCountry(cardType: CardType) {
        _comparisonState.update {
            when (cardType) {
                CardType.PRIMARY -> {
                    it.copy(primaryCountry = null)
                }

                CardType.SECONDARY -> {
                    it.copy(secondaryCountry = null)
                }
            }
        }
    }

    fun clearComparison() {
        _comparisonState.value = ComparisonUiState()
    }
}