package com.elastic.elasticsearch.controller

import com.elastic.elasticsearch.dto.ProductSearchRequest
import com.elastic.elasticsearch.entity.Product
import com.elastic.elasticsearch.service.ProductService
import io.swagger.v3.oas.annotations.Parameter
import org.springdoc.api.annotations.ParameterObject
import org.springdoc.core.converters.models.PageableAsQueryParam
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RequestMapping("/products")
@RestController
class ProductController(private val productService: ProductService) {

    @PostMapping
    fun addProduct(@RequestBody product: Product) {
        productService.addProduct(product)
    }

    @GetMapping
    @PageableAsQueryParam
    fun getProducts(@RequestParam("name") name: String, @Parameter(hidden = true) pageable: Pageable): List<Product> {
        return productService.searchProductsByName(name, pageable)
    }

    @GetMapping("/search")
    @PageableAsQueryParam
    fun search(@ParameterObject @ModelAttribute productSearchRequest: ProductSearchRequest, @Parameter(hidden = true) pageable: Pageable): List<Product> {
        return productService.search(productSearchRequest, pageable)
    }
}