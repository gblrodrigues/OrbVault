package com.gblrod.orbvault.data.repository

import com.gblrod.orbvault.data.dto.CountriesDto
import com.gblrod.orbvault.data.local.dao.FavoriteCountryDao
import com.gblrod.orbvault.data.local.mapper.toEntity

class FavoriteRepository(
    private val favoriteCountryDao: FavoriteCountryDao
) {
    fun getFavorites() = favoriteCountryDao.getFavorites()

    suspend fun addFavorite(country: CountriesDto) {
        favoriteCountryDao.insert(country.toEntity())
    }

    suspend fun removeFavorite(country: CountriesDto) {
        favoriteCountryDao.delete(country.toEntity())
    }

    suspend fun isFavorite(code: String) =
        favoriteCountryDao.isFavorite(code)

    suspend fun removeFavoriteByCode(code: String) {
        favoriteCountryDao.deleteByCode(code)
    }
}