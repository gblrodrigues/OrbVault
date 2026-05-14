package com.gblrod.orbvault.data.countries.repository

import com.gblrod.orbvault.data.countries.local.room.dao.RecentCountryDao
import com.gblrod.orbvault.data.countries.local.room.entity.RecentCountryEntity
import com.gblrod.orbvault.data.countries.local.room.model.RecentCountry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecentCountryRepository(
    private val recentCountryDao: RecentCountryDao
) {
    fun getRecentCountries(): Flow<List<RecentCountry>> {
        return recentCountryDao
            .getRecentCountries()
            .map { countries ->
                countries.map { country ->
                    RecentCountry(
                        code = country.code,
                        name = country.name,
                        official = country.official,
                        capital = country.capital,
                        region = country.region,
                        flagUrl = country.flagUrl
                    )
                }
            }
    }

    suspend fun insertCountry(
        country: RecentCountry
    ) {
        recentCountryDao.insertRecentCountry(
            RecentCountryEntity(
                code = country.code,
                name = country.name,
                official = country.official,
                capital = country.capital,
                region = country.region,
                flagUrl = country.flagUrl
            )
        )

        recentCountryDao.keepOnlyLatestFive()
    }
}