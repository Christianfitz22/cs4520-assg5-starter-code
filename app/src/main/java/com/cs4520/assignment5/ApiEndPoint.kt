package com.cs4520.assignment5

import retrofit2.Call
import retrofit2.http.GET

interface ApiEndPoint {
    @GET(Api.ENDPOINT)
    fun getProductList(): Call<ArrayList<ProductData>>
}