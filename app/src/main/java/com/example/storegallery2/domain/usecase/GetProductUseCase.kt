package com.example.storegallery2.domain.usecase

import com.example.storegallery2.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductUseCase @Inject constructor(private val productRepository: ProductRepository) {

    fun invoke() = productRepository.getProduct()

}