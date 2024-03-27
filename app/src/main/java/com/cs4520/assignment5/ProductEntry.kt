package com.cs4520.assignment5

enum class ProductType {
    FOOD, EQUIPMENT
}

data class ProductEntry(val name: String, val type: ProductType, val date: String?, val price: Double)