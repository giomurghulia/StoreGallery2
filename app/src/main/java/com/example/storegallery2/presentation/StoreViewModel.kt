package com.example.storegallery2.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storegallery2.domain.model.ProductModel
import com.example.storegallery2.domain.usecase.GetProductUseCase
import com.example.storegallery2.toResult
import com.example.storegallery2.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StoreViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase
) : ViewModel(
) {

    private val _state = MutableSharedFlow<Result<List<ListItem>>>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val state get() = _state.asSharedFlow()

    fun getProduct() {
        viewModelScope.launch {
            getProductUseCase.invoke()
                .toResult()
                .collect {
                    when (it) {
                        is Result.Success -> {
                            _state.tryEmit(Result.Success(buildList(it.data)))
                        }
                        is Result.Loading -> {
                            _state.tryEmit(Result.Loading)
                        }
                        is Result.Error -> {
                            _state.tryEmit(Result.Error(it.throwable))
                        }
                    }

                }
        }
    }

    private fun buildList(products: List<ProductModel>): List<ListItem> {
        val items = mutableListOf<ListItem>()

        items.addAll(products.map { ListItem(it.title, it.cover, it.price, it.liked) })
        return items
    }
}