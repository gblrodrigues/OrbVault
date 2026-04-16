package com.gblrod.orbvault.dto

data class CountriesDto(
    val name: NameDto,
    val flags: FlagsDto,
    val capital: List<String?>,
    val population: Long,
    val region: String,
    val subregion: String?,
    val currencies: Map<String, CurrenciesDto?>,
    val languages: Map<String, String?>,
    val timezones: List<String?>
)