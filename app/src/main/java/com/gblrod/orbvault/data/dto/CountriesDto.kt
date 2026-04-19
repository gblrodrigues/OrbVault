package com.gblrod.orbvault.data.dto

data class CountriesDto(
    val name: NameDto,
    val flags: FlagsDto,
    val capital: List<String?>?,
    val population: Long,
    val area: Double?,
    val independent: Boolean?,
    val region: String,
    val subregion: String?,
    val currencies: Map<String, CurrenciesDto?>,
    val languages: Map<String, String?>,
    val timezones: List<String?>,
    val borders: List<String?>,
    val cca3: String?
)