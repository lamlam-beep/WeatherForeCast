package com.example.weatherdemo.data.api

import com.example.weatherdemo.data.models.CurrentWeatherResponse
import com.example.weatherdemo.data.models.ForecastResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class WeatherApiService(
    private val client: HttpClient
) {

    suspend fun getCurrentWeather(
        city: String,
        apiKey: String
    ): CurrentWeatherResponse {
        return client.get("weather") {
            parameter("q", city)
            parameter("appid", apiKey)
            parameter("units", "metric")
        }.body()
    }

    suspend fun getForecast(
        city: String,
        apiKey: String
    ): ForecastResponse {
        return client.get("forecast") {
            parameter("q", city)
            parameter("appid", apiKey)
            parameter("units", "metric")
        }.body()
    }
}

