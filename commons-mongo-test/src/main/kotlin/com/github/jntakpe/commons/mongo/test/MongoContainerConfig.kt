package com.github.jntakpe.commons.mongo.test

import com.github.jntakpe.commons.context.logger
import com.mongodb.MongoClientSettings
import io.micronaut.configuration.mongo.core.DefaultMongoConfiguration
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import javax.inject.Singleton

@Factory
class MongoContainerConfig {

    private val log = logger()

    @Singleton
    @Replaces(bean = MongoClientSettings::class)
    fun testMongoSettings(initConfig: DefaultMongoConfiguration): MongoClientSettings {
        val container = MongoContainer.instance
        val mongoUri = "mongodb://${container.containerIpAddress}:${container.firstMappedPort}"
        log.debug("Setting MongoDB container URI to $mongoUri")
        return initConfig.apply { uri = mongoUri }.buildSettings()
    }
}
