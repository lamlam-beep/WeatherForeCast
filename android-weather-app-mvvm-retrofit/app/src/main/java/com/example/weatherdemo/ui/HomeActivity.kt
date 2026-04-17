package com.example.weatherdemo.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherdemo.databinding.ActivityHomeBinding
import com.example.weatherdemo.presentation.forecast.ForecastAdapter
import com.example.weatherdemo.presentation.home.HomeUiState
import com.example.weatherdemo.presentation.home.HomeViewModel
import com.example.weatherdemo.viewmodel.HomeViewModelFactory
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var forecastAdapter: ForecastAdapter

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(applicationContext)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupListeners()
        observeUiState()

        viewModel.fetchWeather("Hanoi")
    }

    private fun setupRecyclerView() {
        forecastAdapter = ForecastAdapter()
        binding.rvForecast.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = forecastAdapter
        }
    }

    private fun setupListeners() {
        binding.btnSearch.setOnClickListener {
            val city = binding.edtCity.text.toString().trim()
            if (city.isNotEmpty()) {
                viewModel.fetchWeather(city)
            }
        }
    }

    private fun observeUiState() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is HomeUiState.Idle -> Unit

                    is HomeUiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.txtError.visibility = View.GONE
                        binding.contentGroup.visibility = View.GONE
                    }

                    is HomeUiState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.txtError.visibility = View.GONE
                        binding.contentGroup.visibility = View.VISIBLE

                        val weather = state.weather

                        binding.txtCityName.text = weather.city
                        binding.txtTemperature.text = "${weather.temperature}°C"
                        binding.txtDescription.text = weather.description.ifBlank { "Unknown" }
                        binding.txtHumidity.text = "Humidity: ${weather.humidity}%"
                        binding.txtWindSpeed.text = "Wind: ${weather.windSpeed} m/s"
                        binding.txtFeelsLike.text = "Feels like: ${weather.feelsLike}°C"

                        forecastAdapter.submitList(state.forecast)
                    }

                    is HomeUiState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.contentGroup.visibility = View.GONE
                        binding.txtError.visibility = View.VISIBLE
                        binding.txtError.text = state.message
                    }
                }
            }
        }
    }
}