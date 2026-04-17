package com.example.weatherdemo.data
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: CurrentWeatherEntity)

    @Query("SELECT * FROM current_weather WHERE city = :city LIMIT 1")
    suspend fun get(city: String): CurrentWeatherEntity?
}