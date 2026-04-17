package com.example.weatherdemo.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherdemo.data.repository.WeatherRepository
import com.example.weatherdemo.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Idle)
    val uiState: StateFlow<HomeUiState> = _uiState

    fun fetchWeather(city: String) {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading

            val weatherResult = repository.getWeatherData(city)
            val forecastResult = repository.getForecastData(city)

            when {
                weatherResult is Result.Success && forecastResult is Result.Success -> {
                    _uiState.value = HomeUiState.Success(
                        weather = weatherResult.data,
                        forecast = forecastResult.data
                    )
                }

                weatherResult is Result.Error -> {
                    _uiState.value = HomeUiState.Error(
                        weatherResult.exception.message ?: "Failed to load weather"
                    )
                }

                forecastResult is Result.Error -> {
                    _uiState.value = HomeUiState.Error(
                        forecastResult.exception.message ?: "Failed to load forecast"
                    )
                }

                else -> {
                    _uiState.value = HomeUiState.Error("Unknown error")
                }
            }
        }
    }
}