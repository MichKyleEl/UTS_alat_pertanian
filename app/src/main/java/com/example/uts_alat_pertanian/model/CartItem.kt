package com.example.uts_alat_pertanian.model

data class CartItem(
    val id: Int,
    val name: String,
    val price: Double,
    val qty: Int,
    val image_url: String
)
