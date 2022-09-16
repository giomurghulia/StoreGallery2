package com.example.storegallery2.data


import com.example.storegallery2.data.db.ProductDao
import com.example.storegallery2.data.db.ProductEntity
import com.example.storegallery2.domain.model.ProductModel
import com.example.storegallery2.domain.repository.ProductRepository
import com.example.storegallery2.networking.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val productDao: ProductDao
) : ProductRepository {

    override fun getProduct(): Flow<List<ProductModel>> = flow {
        try {

            val response = apiService.getProduct()

            if (response.isSuccessful) {
                emit(response.body()!!)

                if (response.body() != null) {
                    productDao.insertAll(createProductEntityList(response.body()!!))
                }

            } else {
                emit(createProductModelList(productDao.getAll()))
            }
        } catch (e: Exception) {
            emit(createProductModelList(productDao.getAll()))
        }
    }

    private fun createProductEntityList(items: List<ProductModel>): List<ProductEntity> {
        return items.map {
            ProductEntity(it.title, it.cover, it.price, it.liked)
        }
    }

    private fun createProductModelList(items: List<ProductEntity>): List<ProductModel> {
        return items.map {
            ProductModel(it.title, it.cover, it.price, it.liked)
        }
    }
}