package com.gblrod.orbvault.data.weather.remote.api

import com.gblrod.orbvault.data.weather.remote.dto.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET(value = "v1/forecast")
    suspend fun getCurrentWeather(
        @Query(value = "latitude") latitude: Double,
        @Query(value = "longitude") longitude: Double,
        @Query(value = "current") current: String = ApiFields.CURRENT_FIELDS,
        @Query(value = "timezone") timezone: String = "auto",
        @Query(value = "hourly") hourly: String = ApiFields.HOURLY_FIELDS,
    ): WeatherDto
}