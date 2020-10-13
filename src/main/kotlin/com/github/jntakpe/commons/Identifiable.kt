package com.github.jntakpe.commons

import org.bson.types.ObjectId

interface Identifiable {

    companion object {

        const val DB_ID = "_id"
    }

    val id: ObjectId
}
