package com.pradum.inovanttest.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pradum.inovanttest.repository.ProductRepository

class MainViewModelFactory(private  val productRepository: ProductRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(productRepository) as T
    }
}