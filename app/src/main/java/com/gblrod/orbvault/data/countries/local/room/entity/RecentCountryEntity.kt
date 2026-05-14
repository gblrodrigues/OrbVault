package com.gblrod.orbvault.data.countries.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_countries")
data class RecentCountryEntity(
    @PrimaryKey
    val code: String,
    val name: String,
    val official: String,
    val capital: String?,
    val region: String?,
    val flagUrl: String?,
    val visitedAt: Long = System.currentTimeMillis()
)