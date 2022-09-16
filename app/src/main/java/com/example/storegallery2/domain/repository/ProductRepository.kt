package com.example.storegallery2.domain.repository

import com.example.storegallery2.domain.model.ProductModel
import kotlinx.coroutines.flow.Flow


interface ProductRepository {

    fun getProduct(): Flow<List<ProductModel>>
}