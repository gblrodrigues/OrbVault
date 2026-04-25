package com.gblrod.orbvault.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_countries")
data class FavoriteCountryEntity(
    @PrimaryKey val code: String,
    val name: String,
    val capital: String?,
    val region: String?,
    val flagUrl: String?
)