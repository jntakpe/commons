package com.github.jntakpe.commons.grpc.test

import com.github.jntakpe.commons.context.logger
import io.grpc.BindableService
import io.grpc.ManagedChannel
import io.grpc.inprocess.InProcessChannelBuilder
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Parameter
import io.micronaut.context.annotation.Primary
import io.micronaut.context.annotation.Replaces
import io.micronaut.grpc.channels.GrpcManagedChannelFactory
import java.util.function.Supplier

@Factory
@Replaces(factory = GrpcManagedChannelFactory::class)
class GrpcTestConfig(services: List<Supplier<BindableService>>) {

    init {
        GrpcMockServer.start(services.map { it.get() })
    }

    private val logger = logger()

    @Primary
    @Bean(preDestroy = "shutdown")
    fun managedChannel(@Parameter target: String): ManagedChannel {
        logger.info("Creating mocked channel for target $target")
        return InProcessChannelBuilder
            .forName(GrpcMockServer.name)
            .directExecutor()
            .build()
    }
}
