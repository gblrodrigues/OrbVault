package com.gblrod.orbvault.data.network.weather

import com.gblrod.orbvault.data.dto.weather.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query
import com.gblrod.orbvault.data.network.weather.ApiFields.CURRENT_FIELDS
import com.gblrod.orbvault.data.network.weather.ApiFields.HOURLY_FIELDS

interface WeatherApi {
    @GET(value = "v1/forecast")
    suspend fun getCurrentWeather(
        @Query(value = "latitude") latitude: Double,
        @Query(value = "longitude") longitude: Double,
        @Query(value = "current") current: String = CURRENT_FIELDS,
        @Query(value = "timezone") timezone: String = "auto",
        @Query(value = "hourly") hourly: String = HOURLY_FIELDS,
    ): WeatherDto
}