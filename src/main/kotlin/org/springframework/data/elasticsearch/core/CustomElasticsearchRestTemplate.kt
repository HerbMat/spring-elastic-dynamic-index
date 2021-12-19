package org.springframework.data.elasticsearch.core

import com.elastic.elasticsearch.elastic.DomainThreadLocal
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates
import org.springframework.data.elasticsearch.core.query.IndexQuery

class CustomElasticsearchRestTemplate(
    elasticsearchClient: RestHighLevelClient,
    elasticsearchConverter: ElasticsearchConverter,
    private val domainThreadLocal: DomainThreadLocal,
    private val autoCreateIndexEnabled: Boolean): ElasticsearchRestTemplate(elasticsearchClient, elasticsearchConverter) {
    override fun getIndexCoordinatesFor(clazz: Class<*>): IndexCoordinates {
        val indexCoordinates = super.getIndexCoordinatesFor(clazz)

        return IndexCoordinates.of("${indexCoordinates.indexName}_${domainThreadLocal.getTenant()}" )
    }

    override fun indexOps(clazz: Class<*>): IndexOperations {
        return CustomIndexTemplate(this, clazz, domainThreadLocal)
    }

    override fun indexOps(index: IndexCoordinates): IndexOperations {
        return CustomIndexTemplate(this, index, domainThreadLocal)
    }

    override fun doIndex(query: IndexQuery, index: IndexCoordinates): String {
        if (autoCreateIndexEnabled) {
            val indexOperations = indexOps(query.`object`!!.javaClass)
            if(!indexOperations.exists()) {
                indexOperations.createWithMapping()
            }
        }

        return super.doIndex(query, index)
    }
}