package com.gblrod.orbvault.ui.shared.extensions

import com.gblrod.orbvault.data.countries.local.room.model.FavoriteCountry

fun FavoriteCountry.matchesQuery(query: String): Boolean {
    if (query.isBlank()) return true

    return name.contains(
        other = query,
        ignoreCase = true
    ) ||
            official.contains(
                other = query,
                ignoreCase = true
            ) ||
            code.contains(
                other = query,
                ignoreCase = true
            )
}