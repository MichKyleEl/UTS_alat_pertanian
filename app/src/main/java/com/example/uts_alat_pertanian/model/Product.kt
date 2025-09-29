package com.example.uts_alat_pertanian.model

import com.squareup.moshi.Json

data class Product(
    val id: String,
    val name: String,
    val price: String,
    val stock: String,
    @field:Json(name = "image_url") val imageUrl: String?
)
