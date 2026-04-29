package com.gblrod.orbvault.data.countries.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gblrod.orbvault.data.countries.local.room.entity.FavoriteCountryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCountryDao {
    @Query(value = "SELECT * FROM favorite_countries ORDER BY position ASC")
    fun getFavorites(): Flow<List<FavoriteCountryEntity>>

    @Query(value = "SELECT * FROM favorite_countries ORDER BY position ASC")
    suspend fun getFavoritesOnce(): List<FavoriteCountryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countries: List<FavoriteCountryEntity>)

    @Query(value = "DELETE FROM favorite_countries WHERE code = :code")
    suspend fun deleteByCode(code: String)

    @Query(value = "SELECT EXISTS(SELECT 1 FROM favorite_countries WHERE code = :code)")
    suspend fun isFavorite(code: String): Boolean
}