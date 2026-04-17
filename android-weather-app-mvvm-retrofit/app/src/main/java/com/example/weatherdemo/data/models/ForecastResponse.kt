package com.example.weatherdemo.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastResponse(
    val list: List<ForecastItem>
)

@Serializable
data class ForecastItem(
    @SerialName("dt_txt")
    val dateText: String,
    val main: ForecastMainInfo,
    val weather: List<WeatherInfo>
)

@Serializable
data class ForecastMainInfo(
    val temp: Double
)