package com.example.weatherdemo.data.repository


import com.example.weatherdemo.data.CurrentWeatherDao
import com.example.weatherdemo.data.CurrentWeatherEntity
import com.example.weatherdemo.data.ForecastDao
import com.example.weatherdemo.data.ForecastEntity
import com.example.weatherdemo.data.api.WeatherApiService
import com.example.weatherdemo.data.toEntity
import com.example.weatherdemo.utils.Result

class WeatherRepository(
    private val apiService: WeatherApiService,
    private val currentDao: CurrentWeatherDao,
    private val forecastDao: ForecastDao
) {

    private val apiKey = "afc3c7381f6b04827df04988a0a6415a"

    suspend fun getWeatherData(location: String): Result<CurrentWeatherEntity> {
        return try {
            val response = apiService.getCurrentWeather(location, apiKey)

            val entity = response.toEntity(location)
            currentDao.insert(entity)

            Result.Success(entity)
        } catch (e: Exception) {
            val local = currentDao.get(location)

            if (local != null) {
                Result.Success(local) // 👈 OFFLINE MODE
            } else {
                Result.Error(Exception("No data available"))
            }
        }
    }

    suspend fun getForecastData(location: String): Result<List<ForecastEntity>> {
        return try {
            val response = apiService.getForecast(location, apiKey)

            val fiveDays = response.list
                .filterIndexed { index, _ -> index % 8 == 0 }
                .take(5)

            val entityList = fiveDays.toEntity(location)

            forecastDao.deleteByCity(location)
            forecastDao.insertAll(entityList)

            Result.Success(entityList)
        } catch (e: Exception) {
            val local = forecastDao.getByCity(location)

            if (local.isNotEmpty()) {
                Result.Success(local)
            } else {
                Result.Error(Exception("No forecast data"))
            }
        }
    }
}