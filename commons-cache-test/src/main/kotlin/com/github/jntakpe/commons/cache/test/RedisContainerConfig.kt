package com.github.jntakpe.commons.cache.test

import com.github.jntakpe.commons.context.logger
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

    private val log = logger()

    init {
        val container = RedisContainer.instance
        val redisUri = URI.create("redis://${container.containerIpAddress}:${container.firstMappedPort}")
        log.debug("Setting Redis container URI to $redisUri")
        setUri(redisUri)
    }
}
