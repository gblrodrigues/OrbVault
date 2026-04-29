package com.gblrod.orbvault.data.countries.local.room.mapper

import com.gblrod.orbvault.data.countries.remote.dto.CountriesDto
import com.gblrod.orbvault.data.countries.local.room.entity.FavoriteCountryEntity
import com.gblrod.orbvault.data.countries.local.room.model.FavoriteCountry

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