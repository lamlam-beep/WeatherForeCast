package com.example.weatherdemo.data
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ForecastDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<ForecastEntity>)

    @Query("SELECT * FROM forecast WHERE city = :city")
    suspend fun getByCity(city: String): List<ForecastEntity>

    @Query("DELETE FROM forecast WHERE city = :city")
    suspend fun deleteByCity(city: String)
}