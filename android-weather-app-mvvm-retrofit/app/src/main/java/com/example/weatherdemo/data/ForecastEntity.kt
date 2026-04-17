package com.example.weatherdemo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecast")
data class ForecastEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val city: String,
    val dateText: String,
    val temperature: Double,
    val description: String,
    val icon: String
)