package com.gblrod.orbvault.data.countries.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gblrod.orbvault.data.countries.local.room.entity.RecentCountryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentCountryDao {
    @Query(value = """
        SELECT * FROM recent_countries
        ORDER BY visitedAt DESC
        LIMIT 5
        """
    )

    fun getRecentCountries(): Flow<List<RecentCountryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)

    suspend fun insertRecentCountry(
        country: RecentCountryEntity
    )

    @Query(value =  """
        DELETE FROM recent_countries
        WHERE code NOT IN (
            SELECT code
            FROM recent_countries
            ORDER BY visitedAt DESC
            LIMIT 5
        )
        """
    )

    suspend fun keepOnlyLatestFive()
}