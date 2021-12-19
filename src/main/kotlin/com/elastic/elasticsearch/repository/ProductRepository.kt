package com.elastic.elasticsearch.repository

import com.elastic.elasticsearch.entity.Product
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface ProductRepository: ElasticsearchRepository<Product, String> {
    fun findByName(name: String): List<Product>

    fun findByNameContaining(name: String, pageable: Pageable): List<Product>

    fun findByManufacturerAndCategory(manufacturer: String, category: String): List<Product>
}