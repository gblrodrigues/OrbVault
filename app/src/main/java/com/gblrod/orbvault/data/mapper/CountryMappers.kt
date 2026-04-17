package com.gblrod.orbvault.data.mapper

import com.gblrod.orbvault.data.dto.CountriesDto

fun getCurrency(country: CountriesDto): String {
    val firstCurrency = country.currencies.values.firstOrNull() ?: return "N/A"
    return "${firstCurrency.name} (${firstCurrency.symbol ?: ""})"
}

fun getLanguage(country: CountriesDto): String {
    val languages = country.languages

    if (languages.isEmpty()) {
        return "N/A"
    }

    return languages.values.joinToString(separator = "\n")
}

fun getTimezones(
    country: CountriesDto
): String {
    val timezones = country.timezones

    if (timezones.isEmpty()) {
        return "N/A"
    }

    return timezones.joinToString(separator = "\n")
}