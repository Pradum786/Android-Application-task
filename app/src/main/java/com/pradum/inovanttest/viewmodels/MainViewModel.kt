package com.pradum.inovanttest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pradum.inovanttest.models.Product
import com.pradum.inovanttest.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val productRepository: ProductRepository): ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.getProduct()
        }
    }
    val liveData:LiveData<Product>
        get() = productRepository.product
}