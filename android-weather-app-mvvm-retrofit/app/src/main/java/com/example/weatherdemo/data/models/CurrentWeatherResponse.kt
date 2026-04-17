package com.example.weatherdemo.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherResponse(
    val name: String ="",
    val main: MainInfo = (MainInfo(20.0, 20,20.0)),
    val weather: List<WeatherInfo> = emptyList(),
    val wind: WindInfo = WindInfo(20.0)
)

@Serializable
data class MainInfo(
    val temp: Double,
    val humidity: Int,
    @SerialName("feels_like")
    val feelsLike: Double
)

@Serializable
data class WeatherInfo(
    val description: String = "",
    val icon: String = ""
)

@Serializable
data class WindInfo(
    val speed: Double
)