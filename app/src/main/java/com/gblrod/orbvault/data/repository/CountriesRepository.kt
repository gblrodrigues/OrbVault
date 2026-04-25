package com.gblrod.orbvault.data.repository

import com.gblrod.orbvault.data.dto.CountriesDto
import com.gblrod.orbvault.data.network.CountriesAPI

private const val MAX_BORDERS = 5

class CountriesRepository(
    private val api: CountriesAPI
) {
    suspend fun fetchCountry(name: String): CountriesDto? {
        return api.findCountry(name = name, fullText = true)
            .firstOrNull { it.name.common.equals(other = name, ignoreCase = true) }
    }

    suspend fun getRandomCountry(): CountriesDto {
        val countries = api.getAllCountries()
        val random = countries.random()
        return api.findCountryByCode(random.cca3)
    }

    suspend fun getBorders(country: CountriesDto): List<CountriesDto> {
        val codes = country.borders?.joinToString(separator = ",")

        if (codes?.isBlank() ?: false) return emptyList()

        return api.getBordersCountries(codes = codes).take(n = MAX_BORDERS)
    }

    suspend fun fetchCountryByCode(code: String?) =
        api.findCountryByCode(code = code)
}