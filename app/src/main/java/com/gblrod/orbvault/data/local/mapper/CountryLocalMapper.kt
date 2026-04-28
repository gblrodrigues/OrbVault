package com.gblrod.orbvault.data.local.mapper

import com.gblrod.orbvault.data.dto.countries.CountriesDto
import com.gblrod.orbvault.data.local.entity.FavoriteCountryEntity
import com.gblrod.orbvault.data.local.model.FavoriteCountry

fun CountriesDto.toEntity(position: Int) = FavoriteCountryEntity(
    code = cca3,
    name = name.common,
    capital = capital?.firstOrNull(),
    region = region,
    flagUrl = flags.png,
    official = name.official,
    position = position
)

fun FavoriteCountryEntity.toDomain() = FavoriteCountry(
    code = code,
    name = name,
    capital = capital,
    region = region,
    flagUrl = flagUrl,
    official = official
)

fun FavoriteCountry.toEntity(position: Int) = FavoriteCountryEntity(
    code = code,
    name = name,
    official = official,
    capital = capital,
    region = region,
    flagUrl = flagUrl,
    position = position
)