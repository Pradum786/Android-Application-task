package com.pradum.inovanttest.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pradum.inovanttest.api.ProductService
import com.pradum.inovanttest.models.Product

class ProductRepository(private val productService: ProductService) {
    private val productlivedata=MutableLiveData<Product>()

    val product:LiveData<Product> get() = productlivedata
    suspend fun getProduct(){

        val result=productService.getProduct("en","KWD")
        if (result?.body() != null){
            productlivedata.postValue(result.body())
        }
    }

}