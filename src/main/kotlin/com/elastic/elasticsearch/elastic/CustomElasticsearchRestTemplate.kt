package com.elastic.elasticsearch.elastic

import org.elasticsearch.client.RestHighLevelClient
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates

class CustomElasticsearchRestTemplate(elasticsearchClient: RestHighLevelClient, elasticsearchConverter: ElasticsearchConverter, private val domainThreadLocal: DomainThreadLocal): ElasticsearchRestTemplate(elasticsearchClient, elasticsearchConverter) {
    override fun getIndexCoordinatesFor(clazz: Class<*>): IndexCoordinates {
        val indexCoordinates = super.getIndexCoordinatesFor(clazz)

        return IndexCoordinates.of("${indexCoordinates.indexName}_${domainThreadLocal.getTenant()}" )
    }
}