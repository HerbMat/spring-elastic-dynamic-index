package com.elastic.elasticsearch.service

import com.elastic.elasticsearch.dto.ProductSearchRequest
import com.elastic.elasticsearch.elastic.DomainThreadLocal
import com.elastic.elasticsearch.entity.Product
import com.elastic.elasticsearch.repository.ProductRepository
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.Operator
import org.elasticsearch.index.query.QueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate
import org.springframework.data.elasticsearch.core.SearchHit
import org.springframework.data.elasticsearch.core.SearchHits
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import org.springframework.data.elasticsearch.core.query.Query
import org.springframework.stereotype.Service

@Service
class ProductService(private val productRepository: ProductRepository, private val elasticsearchRestTemplate: ElasticsearchOperations, private val domainThreadLocal: DomainThreadLocal) {

    fun addProduct(product: Product) {
        productRepository.save(product)
    }

    fun searchProductsByName(name: String, pageable: Pageable): List<Product> {
        return productRepository.findByNameContaining(name, pageable)
    }

    fun search(productSearchRequest: ProductSearchRequest, pageable: Pageable): List<Product> {
        var queryBuilder: BoolQueryBuilder = QueryBuilders.boolQuery()
        if (productSearchRequest.quantity != null) {
            queryBuilder = queryBuilder.must(QueryBuilders.matchQuery("quantity", productSearchRequest.quantity))
        }
        if (productSearchRequest.categories.isNotEmpty()) {
            queryBuilder = queryBuilder.must(QueryBuilders.termsQuery("category", *productSearchRequest.categories.toTypedArray()))
        }
        if (productSearchRequest.name != null) {
            queryBuilder = queryBuilder.must(QueryBuilders.wildcardQuery("name", "*" + productSearchRequest.name + "*"))
        }
        if (productSearchRequest.minPrice != null && productSearchRequest.maxPrice != null) {
            queryBuilder = queryBuilder.must(QueryBuilders.rangeQuery("price").from(productSearchRequest.minPrice).to(productSearchRequest.maxPrice))
        }
        val query: Query = NativeSearchQueryBuilder().withQuery(queryBuilder).withPageable(pageable).build()
        val results : SearchHits<Product> = elasticsearchRestTemplate.search(query, Product::class.java)

        return results.map { it.content }.toList()
    }
}