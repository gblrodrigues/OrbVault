package com.gblrod.orbvault.dto

data class CountriesDto(
    val name: NameDto,
    val flags: FlagsDto,
    val capital: List<String?>,
    val population: Long,
    val region: String
)
