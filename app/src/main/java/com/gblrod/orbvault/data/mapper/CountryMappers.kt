package com.gblrod.orbvault.data.mapper

import com.gblrod.orbvault.data.dto.CountriesDto

private const val MAX_LANGUAGES = 3
private const val MAX_TIMEZONES = 2

fun getCurrency(country: CountriesDto): String {
    val firstCurrency = country.currencies?.values?.firstOrNull() ?: return "N/A"
    return "${firstCurrency.name} (${firstCurrency.symbol ?: ""})"
}

fun getLanguage(country: CountriesDto): String {
    val languages = country.languages ?: return "N/A"

    if (languages.isEmpty()) {
        return "N/A"
    }

    return languages.values.take(n = MAX_LANGUAGES).joinToString(separator = ", ")
}

fun getTimezones(
    country: CountriesDto
): String {
    val timezones = country.timezones ?: return "N/A"

    if (timezones.isEmpty()) {
        return "N/A"
    }

    return timezones.take(n = MAX_TIMEZONES).joinToString(separator = ", ")
}

fun isCountryCode(country: String): Boolean {
    return country.length == 3 && country.all { it.isLetter() }
}