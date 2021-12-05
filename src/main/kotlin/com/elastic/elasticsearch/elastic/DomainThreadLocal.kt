package com.elastic.elasticsearch.elastic

import org.springframework.stereotype.Component

@Component
class DomainThreadLocal {
    fun getTenant(): String {
        return "tenant"
    }
}