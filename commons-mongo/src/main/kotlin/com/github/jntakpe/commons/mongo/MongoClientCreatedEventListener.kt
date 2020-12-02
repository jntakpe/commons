package com.github.jntakpe.commons.mongo

import brave.Tracing
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactor.client.tracing.toTracingReactor
import io.micronaut.context.annotation.Requires
import io.micronaut.context.event.BeanCreatedEvent
import io.micronaut.context.event.BeanCreatedEventListener
import javax.inject.Singleton

@Singleton
@Requires(beans = [Tracing::class])
class MongoClientCreatedEventListener(private val tracing: Tracing) : BeanCreatedEventListener<MongoClient> {

    override fun onCreated(event: BeanCreatedEvent<MongoClient>): MongoClient = event.bean.toTracingReactor(tracing)
}
