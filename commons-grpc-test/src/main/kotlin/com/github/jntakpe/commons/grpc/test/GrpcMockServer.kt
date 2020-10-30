package com.github.jntakpe.commons.grpc.test

import io.grpc.BindableService
import io.grpc.Server
import io.grpc.inprocess.InProcessServerBuilder
import io.grpc.util.MutableHandlerRegistry

object GrpcMockServer {

    val name: String = InProcessServerBuilder.generateName()
    private val builder = InProcessServerBuilder.forName(name).directExecutor()
    private var server: Server? = null

    fun start(services: List<BindableService>) {
        if (server == null || server?.isShutdown == true) {
            server = builder
                .fallbackHandlerRegistry(MutableHandlerRegistry().apply { services.forEach { addService(it) } })
                .build()
                .start()
        }
    }
}
