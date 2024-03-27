package com.cs4520.assignment5

class ProductRepository {
    private val retrofit = RetrofitClient.getRetrofitInstance().create(ApiEndPoint::class.java)

    fun getAllProducts() = retrofit.getProductList()
}