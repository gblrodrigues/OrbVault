package com.gblrod.orbvault.data.repository

import com.gblrod.orbvault.data.dto.CountriesDto
import com.gblrod.orbvault.data.network.CountriesAPI

private const val MAX_BORDERS = 5

class CountriesRepository(
    private val api: CountriesAPI
) {
    suspend fun fetchCountry(name: String) = api.findCountry(name).firstOrNull()

    suspend fun getRandomCountry() = api.getRandomCountry().random()

    suspend fun getBorders(country: CountriesDto): List<CountriesDto> {
        val codes = country.borders
            .filterNotNull()
            .joinToString(separator = ",")

        if (codes.isBlank()) return emptyList()

        return api.getBordersCountries(codes = codes)
            .take(n = MAX_BORDERS)
    }

    suspend fun fetchCountryByCode(code: String) =
        api.findCountryByCode(code = code)
}