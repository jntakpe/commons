package com.github.jntakpe.commons.mongo.test

import com.github.jntakpe.commons.context.logger
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import io.micronaut.configuration.mongo.core.DefaultMongoConfiguration
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import javax.inject.Singleton

@Factory
class MongoContainerConfig {

    private val log = logger()

    @Singleton
    @Bean(preDestroy = "close")
    @Replaces(bean = MongoClient::class)
    fun mongoContainerClient(initConfig: DefaultMongoConfiguration): MongoClient {
        val container = MongoContainer.instance
        val mongoUri = "mongodb://${container.containerIpAddress}:${container.firstMappedPort}"
        log.debug("Setting MongoDB container URI to $mongoUri")
        val testConfig = initConfig.apply { uri = mongoUri }
        return MongoClients.create(testConfig.buildSettings())
    }
}
