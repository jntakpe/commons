package com.github.jntakpe.commons.cache.test

import io.micronaut.configuration.lettuce.AbstractRedisConfiguration
import io.micronaut.configuration.lettuce.DefaultRedisConfiguration
import io.micronaut.context.annotation.Primary
import io.micronaut.context.annotation.Replaces
import java.net.URI
import javax.inject.Singleton

@Primary
@Singleton
@Replaces(DefaultRedisConfiguration::class)
class RedisContainerConfig : AbstractRedisConfiguration() {

    init {
        val container = RedisContainer.instance
        setUri(URI.create("redis://${container.containerIpAddress}:${container.firstMappedPort}"))
    }
}
