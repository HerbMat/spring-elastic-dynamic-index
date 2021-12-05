package com.elastic.elasticsearch.controller

import com.elastic.elasticsearch.dto.ProductSearchRequest
import com.elastic.elasticsearch.entity.Product
import com.elastic.elasticsearch.service.ProductService
import org.springdoc.api.annotations.ParameterObject
import org.springframework.web.bind.annotation.*

@RequestMapping("/products")
@RestController
class ProductController(private val productService: ProductService) {

    @PostMapping
    fun addProduct(@RequestBody product: Product) {
        productService.addProduct(product)
    }

    @GetMapping
    fun getProducts(@RequestParam("name") name: String): List<Product> {
        return productService.searchProductsByName(name)
    }

    @GetMapping("/search")
    fun search(@ParameterObject @ModelAttribute productSearchRequest: ProductSearchRequest): List<Product> {
        return productService.search(productSearchRequest)
    }
}