package com.example.uts_alat_pertanian.api

import com.example.uts_alat_pertanian.model.Product
import com.example.uts_alat_pertanian.model.ApiResponse
import retrofit2.Call
import retrofit2.http.*
import com.example.uts_alat_pertanian.model.CartItem

interface ProductApiService {
    @GET("get_cart.php")
    fun getCart(): Call<List<CartItem>>


    @FormUrlEncoded
    @POST("add_to_cart.php")
    fun addToCart(
        @Field("product_id") productId: Int,
        @Field("qty") qty: Int
    ): Call<ApiResponse>

    @POST("checkout.php")
    fun checkout(): Call<ApiResponse>

}
