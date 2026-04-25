package com.gblrod.orbvault.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gblrod.orbvault.data.local.entity.FavoriteCountryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCountryDao {
    @Query(value = "SELECT * FROM favorite_countries")
    fun getFavorites(): Flow<List<FavoriteCountryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(country: FavoriteCountryEntity)

    @Delete
    suspend fun delete(country: FavoriteCountryEntity)

    @Query(value = "SELECT EXISTS(SELECT 1 FROM favorite_countries WHERE code = :code)")
    suspend fun isFavorite(code: String): Boolean
}