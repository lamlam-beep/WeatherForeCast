package com.example.weatherdemo.presentation.home

import com.example.weatherdemo.data.CurrentWeatherEntity
import com.example.weatherdemo.data.ForecastEntity


sealed class HomeUiState {
    object Idle : HomeUiState()
    object Loading : HomeUiState()

    data class Success(
        val weather: CurrentWeatherEntity,
        val forecast: List<ForecastEntity>
    ) : HomeUiState()

    data class Error(val message: String) : HomeUiState()
}