package com.example.uts_alat_pertanian.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    val success: Boolean,
    val message: String,
    val data: UserData?
)

data class UserData(
    val id: Int,
    val username: String,
    val email: String,
    @SerializedName("profile_pic") val profilePic: String
)
