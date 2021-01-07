package com.github.jntakpe.commons.mongo

import brave.Tracing
import com.mongodb.MongoClientSettings
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import com.mongodb.reactor.client.tracing.TracingReactorMongoClient
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.context.annotation.Requires
import org.litote.kmongo.reactivestreams.withKMongo
import org.litote.kmongo.serialization.changeIdController
import javax.inject.Singleton

@Factory
class MongoConfig(private val settings: MongoClientSettings) {

    init {
        changeIdController(IdentifiableIdController())
    }

    @Singleton
    fun reactorDatabase(client: MongoClient) = client.getDatabase(settings.applicationName).withKMongo()

    @Singleton
    @Bean(preDestroy = "close")
    @Requires(beans = [Tracing::class])
    @Replaces(bean = MongoClient::class)
    fun tracingClient(settings: MongoClientSettings, tracing: Tracing) = TracingReactorMongoClient(MongoClients.create(settings), tracing)
}
