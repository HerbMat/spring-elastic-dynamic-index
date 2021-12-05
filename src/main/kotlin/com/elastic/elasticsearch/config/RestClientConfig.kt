package com.elastic.elasticsearch.config

import com.elastic.elasticsearch.elastic.CustomElasticsearchRestTemplate
import com.elastic.elasticsearch.elastic.DomainThreadLocal
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.RestClients
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter

@Configuration
class RestClientConfig: AbstractElasticsearchConfiguration() {

    @Bean
    override fun elasticsearchClient(): RestHighLevelClient {
        val clientConfiguration = ClientConfiguration.builder()
            .connectedTo("localhost:9200")
            .build()
        return RestClients.create(clientConfiguration).rest()
    }

    @Bean(name = ["elasticsearchOperations", "elasticsearchTemplate"])
    override fun elasticsearchOperations(elasticsearchConverter: ElasticsearchConverter, elasticsearchClient: RestHighLevelClient): ElasticsearchOperations {
        val template = CustomElasticsearchRestTemplate(
            elasticsearchClient,
            elasticsearchConverter,
            DomainThreadLocal()
        )
        template.refreshPolicy = refreshPolicy()
        return template
    }


}