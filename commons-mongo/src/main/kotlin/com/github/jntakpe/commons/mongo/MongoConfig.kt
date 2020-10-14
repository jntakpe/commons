package com.github.jntakpe.commons.mongo

import com.mongodb.MongoClientSettings
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoDatabase
import io.micronaut.context.annotation.Factory
import org.litote.kmongo.reactivestreams.withKMongo
import org.litote.kmongo.serialization.changeIdController
import javax.inject.Singleton

@Factory
class MongoConfig(private val settings: MongoClientSettings) {

    init {
        changeIdController(IdentifiableIdController())
    }

    @Singleton
    fun databaseClient(client: MongoClient): MongoDatabase = client.getDatabase(settings.applicationName).withKMongo()
}
