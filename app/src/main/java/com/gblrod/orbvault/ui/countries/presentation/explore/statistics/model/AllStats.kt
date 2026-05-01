package com.gblrod.orbvault.ui.countries.presentation.explore.statistics.model

import com.gblrod.orbvault.data.countries.remote.dto.CountriesDto

data class AllStats(
    val totalCountries: Int,
    val totalPopulation: Long,
    val totalArea: Double,
    val mostPopulous: CountriesDto?,
    val largest: CountriesDto?
)