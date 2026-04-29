package com.gblrod.orbvault.data.countries.mapper

import com.gblrod.orbvault.data.countries.remote.dto.CountriesDto

private const val MAX_LANGUAGES = 2
private const val MAX_TIMEZONES = 2
private const val NOT_AVAILABLE = "N/A"

fun getCurrency(country: CountriesDto): String {
    val firstCurrency = country.currencies?.values?.firstOrNull() ?: return NOT_AVAILABLE
    return "${firstCurrency.name} (${firstCurrency.symbol ?: ""})"
}

fun getLanguage(country: CountriesDto): String {
    val languages = country.languages ?: return NOT_AVAILABLE

    if (languages.isEmpty()) {
        return NOT_AVAILABLE
    }

    return languages.values.take(n = MAX_LANGUAGES).joinToString(separator = ", ")
}

fun getTimezones(
    country: CountriesDto
): String {
    val timezones = country.timezones ?: return NOT_AVAILABLE

    if (timezones.isEmpty()) {
        return NOT_AVAILABLE
    }

    return timezones.take(n = MAX_TIMEZONES).joinToString(separator = ", ")
}

fun isCountryCode(country: String): Boolean {
    return country.length == 3 && country.all { it.isLetter() }
}