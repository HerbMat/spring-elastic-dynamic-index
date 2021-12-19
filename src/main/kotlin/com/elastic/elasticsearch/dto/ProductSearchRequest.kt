package com.elastic.elasticsearch.dto

data class ProductSearchRequest(
    val minPrice: Double? = null,
    val maxPrice: Double? = null,
    val quantity: Int? = null,
    val categories: List<String> = listOf(),
    val description: String? = null,
    val manufacturer: String? = null,
    val name: String? = null
)