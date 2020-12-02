package com.github.jntakpe.commons.mongo

import brave.Tracing
import brave.mongodb.MongoDBTracing
import io.micronaut.configuration.mongo.core.AbstractMongoConfiguration
import io.micronaut.context.annotation.Requires
import io.micronaut.context.event.BeanCreatedEvent
import io.micronaut.context.event.BeanCreatedEventListener
import javax.inject.Singleton

@Singleton
@Requires(beans = [Tracing::class])
class MongoConfigurationCreatedEventListener(private val tracing: Tracing) : BeanCreatedEventListener<AbstractMongoConfiguration> {

    override fun onCreated(event: BeanCreatedEvent<AbstractMongoConfiguration>): AbstractMongoConfiguration {
        return event.bean.apply { clientSettings.addCommandListener(MongoDBTracing.create(tracing).commandListener()) }
    }
}
