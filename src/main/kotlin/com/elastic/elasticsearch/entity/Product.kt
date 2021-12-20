package com.elastic.elasticsearch.entity

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = "productindex", createIndex = false)
data class Product(
    @Id
    var id: String? = null,

    @Field(type = FieldType.Text, name = "name", fielddata = true)
    var name: String? = null,

    @Field(type = FieldType.Double, name = "price")
    val price: Double? = null,

    @Field(type = FieldType.Integer, name = "quantity")
    val quantity: Int? = null,

    @Field(type = FieldType.Keyword, name = "category")
    val category: String? = null,

    @Field(type = FieldType.Text, name = "desc")
    val description: String? = null,

    @Field(type = FieldType.Keyword, name = "manufacturer")
    val manufacturer: String? = null,

    @Field(type= FieldType.Object, name = "creator")
    val creator: Creator = Creator("test", "test"),

    @Field(type = FieldType.Nested, name = "secondary")
    val secondary: Creator = Creator("secondary", "secondary"),

    @Field(type = FieldType.Nested, name = "elements")
    val elements: List<Element> = listOf(Element(), Element())
)