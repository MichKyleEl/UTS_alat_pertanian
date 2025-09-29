package com.example.uts_alat_pertanian.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientProfile {
    private const val BASE_URL = "http://10.0.2.2/farm_db/"

    val instance: ProfileApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProfileApiService::class.java)
    }
}
