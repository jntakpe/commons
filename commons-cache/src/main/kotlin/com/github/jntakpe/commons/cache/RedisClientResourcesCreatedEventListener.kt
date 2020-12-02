package com.github.jntakpe.commons.cache

import brave.Tracing
import io.lettuce.core.resource.ClientResources
import io.lettuce.core.tracing.BraveTracing
import io.micronaut.context.annotation.Requires
import io.micronaut.context.env.Environment
import io.micronaut.context.event.BeanCreatedEvent
import io.micronaut.context.event.BeanCreatedEventListener
import javax.inject.Singleton

@Singleton
@Requires(beans = [Tracing::class], notEnv = [Environment.TEST])
class RedisClientResourcesCreatedEventListener(private val tracing: Tracing) : BeanCreatedEventListener<ClientResources> {

    override fun onCreated(event: BeanCreatedEvent<ClientResources>): ClientResources {
        return event.bean.mutate().tracing(BraveTracing.create(tracing)).build()
    }
}
