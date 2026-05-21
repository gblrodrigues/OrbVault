package com.gblrod.orbvault.ui.shared.extensions

import com.gblrod.orbvault.data.countries.remote.dto.CountriesDto

fun CountriesDto.matchesQuery(query: String): Boolean {
    return name.common.contains(
        other = query,
        ignoreCase = true
    ) ||
            name.official.contains(
                other = query,
                ignoreCase = true
            ) ||
            cca3.contains(
                other = query,
                ignoreCase = true
            )
}