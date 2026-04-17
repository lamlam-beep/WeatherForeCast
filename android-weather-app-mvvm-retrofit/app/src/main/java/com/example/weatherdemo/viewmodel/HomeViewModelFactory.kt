package com.example.weatherdemo.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherdemo.data.WeatherDatabase
import com.example.weatherdemo.data.api.KtorClientProvider
import com.example.weatherdemo.data.api.WeatherApiService
import com.example.weatherdemo.data.repository.WeatherRepository
import com.example.weatherdemo.presentation.home.HomeViewModel

class HomeViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val db = WeatherDatabase.getInstance(context)

        val repository = WeatherRepository(
            WeatherApiService(KtorClientProvider.client),
            db.currentDao(),
            db.forecastDao()
        )

        return HomeViewModel(repository) as T
    }
}