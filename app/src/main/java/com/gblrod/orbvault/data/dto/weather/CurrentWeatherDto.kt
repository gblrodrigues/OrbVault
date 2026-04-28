package com.gblrod.orbvault.data.dto.weather

import com.google.gson.annotations.SerializedName

data class CurrentWeatherDto(
    @SerializedName(value = "temperature_2m")
    val temperature2M: Double,

    @SerializedName(value = "weather_code")
    val weatherCode: Int,

    @SerializedName(value = "wind_speed_10m")
    val windSpeed10m: Double,

    @SerializedName(value = "apparent_temperature")
    val apparentTemperature: Double,

    @SerializedName(value = "relative_humidity_2m")
    val humidity: Int,

    val time: String
)