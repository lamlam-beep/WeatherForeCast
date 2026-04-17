package com.example.weatherdemo.data
import com.example.weatherdemo.data.models.*

fun CurrentWeatherResponse.toEntity(city: String): CurrentWeatherEntity {
    return CurrentWeatherEntity(
        city = city,
        temperature = main.temp,
        humidity = main.humidity,
        feelsLike = main.feelsLike,
        description = weather.firstOrNull()?.description ?: "",
        icon = weather.firstOrNull()?.icon ?: "",
        windSpeed = wind.speed,
        updatedAt = System.currentTimeMillis()
    )
}

fun List<ForecastItem>.toEntity(city: String): List<ForecastEntity> {
    return map {
        ForecastEntity(
            city = city,
            dateText = it.dateText,
            temperature = it.main.temp,
            description = it.weather.firstOrNull()?.description ?: "",
            icon = it.weather.firstOrNull()?.icon ?: ""
        )
    }
}