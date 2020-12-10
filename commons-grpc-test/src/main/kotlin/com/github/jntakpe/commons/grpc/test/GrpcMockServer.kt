package com.github.jntakpe.commons.grpc.test

import com.github.jntakpe.commons.context.logger
import io.grpc.BindableService
import io.grpc.Server
import io.grpc.inprocess.InProcessServerBuilder
import io.grpc.util.MutableHandlerRegistry

object GrpcMockServer {

    val name: String = InProcessServerBuilder.generateName()
    private val builder = InProcessServerBuilder.forName(name).directExecutor()
    private var server: Server? = null
    private val log = logger()

    fun start(services: List<BindableService>) {
        if (server == null || server?.isShutdown == true) {
            server = builder
                .fallbackHandlerRegistry(MutableHandlerRegistry().apply { services.forEach { addService(it) } })
                .build()
                .start()
            log.debug("GRPC Mock server '$server' started")
        }
    }
}
