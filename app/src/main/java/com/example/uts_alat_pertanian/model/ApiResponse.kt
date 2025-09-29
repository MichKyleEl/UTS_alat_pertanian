package com.example.uts_alat_pertanian.model

data class ApiResponse(
    val success: Boolean,
    val message: String? = null,
    val new_stock: Int? = null
)
