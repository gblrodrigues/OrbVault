package com.gblrod.orbvault.data.local.mapper

import com.gblrod.orbvault.data.dto.CountriesDto
import com.gblrod.orbvault.data.local.model.FavoriteCountry
import com.gblrod.orbvault.data.local.entity.FavoriteCountryEntity

fun CountriesDto.toEntity() = FavoriteCountryEntity(
    code = cca3 ?: error("Country code cannot be null"),
    name = name.common,
    capital = capital?.firstOrNull(),
    region = region,
    flagUrl = flags.png
)

fun FavoriteCountryEntity.toDomain() = FavoriteCountry(
    code = code,
    name = name,
    capital = capital,
    region = region,
    flagUrl = flagUrl
)