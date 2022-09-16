package com.example.storegallery2.networking

import com.example.storegallery2.domain.model.ProductModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("05d71804-4628-4269-ac03-f86e9960a0bb")
    suspend fun getProduct(): Response<List<ProductModel>>
}