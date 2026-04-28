package com.gblrod.orbvault.data.repository.favorites

import androidx.room.Transaction
import com.gblrod.orbvault.data.local.dao.FavoriteCountryDao
import com.gblrod.orbvault.data.local.mapper.toDomain
import com.gblrod.orbvault.data.local.mapper.toEntity
import com.gblrod.orbvault.data.local.model.FavoriteCountry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRepository(
    private val favoriteCountryDao: FavoriteCountryDao
) {
    fun getFavorites(): Flow<List<FavoriteCountry>> {
        return favoriteCountryDao.getFavorites()
            .map { list -> list.map { it.toDomain() } }
    }

    suspend fun addFavorite(country: FavoriteCountry, index: Int) {
        val current = favoriteCountryDao.getFavoritesOnce().toMutableList()
        val safeIndex = index.coerceIn(0, current.size)
        current.add(safeIndex, element = country.toEntity(position = 0))

        val reordered = current.mapIndexed { i, item ->
            item.copy(position = i)
        }

        favoriteCountryDao.insertAll(countries = reordered)
    }

    @Transaction
    suspend fun removeFavorite(code: String) {
        favoriteCountryDao.deleteByCode(code)

        val current = favoriteCountryDao.getFavoritesOnce()
        val reordered = current.mapIndexed { index, item ->
            item.copy(position = index)
        }

        favoriteCountryDao.insertAll(countries = reordered)
    }

    @Transaction
    suspend fun restoreFavorite(country: FavoriteCountry, index: Int) {
        val current = favoriteCountryDao.getFavoritesOnce().toMutableList()

        current.removeAll { it.code == country.code }

        val safeIndex = index.coerceIn(0, current.size)

        current.add(safeIndex, element = country.toEntity(position = 0))

        val reordered = current.mapIndexed { i, item ->
            item.copy(position = i)
        }

        favoriteCountryDao.insertAll(countries = reordered)
    }

    suspend fun isFavorite(code: String) =
        favoriteCountryDao.isFavorite(code)
}