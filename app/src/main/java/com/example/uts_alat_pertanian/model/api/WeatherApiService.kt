package com.example.uts_alat_pertanian.api

import com.example.uts_alat_pertanian.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("v1/current.json")
    suspend fun getWeather(
        @Query("key") apiKey: String,
        @Query("q") city: String
    ): WeatherResponse
}
