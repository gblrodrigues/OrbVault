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
        viewModelScope.launch {
            val fullCountry = repository.fetchCountryByCode(country.cca3)

            _comparisonState.update {
                when (it.selectedCard) {
                    CardType.PRIMARY -> {
                        it.copy(
                            primaryCountry = fullCountry,
                            showBottomSheet = false
                        )
                    }

                    CardType.SECONDARY -> {
                        it.copy(
                            secondaryCountry = fullCountry,
                            showBottomSheet = false
                        )
                    }

                    null -> it
                }
            }
        }
    }

    fun dismissBottomSheet() {
        _comparisonState.update {
            it.copy(showBottomSheet = false)
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