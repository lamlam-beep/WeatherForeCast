package com.example.weatherdemo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_weather")
data class CurrentWeatherEntity(
    @PrimaryKey
    val city: String,
    val temperature: Double,
    val humidity: Int,
    val feelsLike: Double,
    val description: String,
    val icon: String,
    val windSpeed: Double,
    val updatedAt: Long
)