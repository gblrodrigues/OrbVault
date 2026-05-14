package com.gblrod.orbvault.data.countries.local.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gblrod.orbvault.data.countries.local.room.dao.FavoriteCountryDao
import com.gblrod.orbvault.data.countries.local.room.dao.RecentCountryDao
import com.gblrod.orbvault.data.countries.local.room.entity.FavoriteCountryEntity
import com.gblrod.orbvault.data.countries.local.room.entity.RecentCountryEntity

@Database(
    entities = [FavoriteCountryEntity::class, RecentCountryEntity::class],
    version = 4,
    exportSchema = false
)
abstract class OrbVaultDataBase : RoomDatabase() {
    abstract fun favoriteCountryDao(): FavoriteCountryDao
    abstract fun recentCountryDao(): RecentCountryDao
}