package org.springframework.data.elasticsearch.core

import com.elastic.elasticsearch.elastic.DomainThreadLocal
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates

internal class CustomIndexTemplate: RestIndexTemplate {
    private val domainThreadLocal: DomainThreadLocal
    internal constructor(restTemplate: ElasticsearchRestTemplate, boundIndex: IndexCoordinates, domainThreadLocal: DomainThreadLocal): super(restTemplate, boundIndex) {
        this.domainThreadLocal = domainThreadLocal
    }
    internal constructor(restTemplate: ElasticsearchRestTemplate, boundClass: Class<*>, domainThreadLocal: DomainThreadLocal): super(restTemplate, boundClass) {
        this.domainThreadLocal = domainThreadLocal
    }

    override fun getIndexCoordinates(): IndexCoordinates {
        val indexCoordinates = super.getIndexCoordinates()

        return IndexCoordinates.of("${indexCoordinates.indexName}_${domainThreadLocal.getTenant()}" )
    }

}