package com.example.uts_alat_pertanian.api

import com.example.uts_alat_pertanian.model.UserResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ProfileApiService {
    @FormUrlEncoded
    @POST("save_profile.php")
    fun saveUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("avatarUrl") avatarUrl: String
    ): Call<UserResponse>
}
