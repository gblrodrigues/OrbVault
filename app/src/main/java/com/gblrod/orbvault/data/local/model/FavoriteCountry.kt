package com.gblrod.orbvault.data.local.model

data class FavoriteCountry(
    val code: String,
    val name: String,
    val official: String,
    val capital: String?,
    val region: String?,
    val flagUrl: String?
)