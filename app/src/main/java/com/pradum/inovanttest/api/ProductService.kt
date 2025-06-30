package com.pradum.inovanttest.api

import com.pradum.inovanttest.models.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {
    @GET("rest/V1/productdetails/6701/253620")
    suspend fun getProduct(@Query("lang") lang:String,@Query("store") store:String):Response<Product>
}