package com.github.jntakpe.commons.cache.test

import org.testcontainers.containers.GenericContainer

object RedisContainer {

    const val REDIS_DOCKER_IMAGE = "redis"
    const val REDIS_VERSION = "6.0.9"
    val instance by lazy {
        GenericContainer<Nothing>("$REDIS_DOCKER_IMAGE:$REDIS_VERSION")
            .apply {
                withExposedPorts(6379)
                start()
            }
    }
}
