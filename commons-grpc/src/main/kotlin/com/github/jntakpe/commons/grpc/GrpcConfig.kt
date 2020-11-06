package com.github.jntakpe.commons.grpc

import com.github.jntakpe.commons.context.ReactorExceptionLogger
import io.envoyproxy.pgv.ExplicitValidatorIndex
import io.envoyproxy.pgv.grpc.ValidatingServerInterceptor
import io.grpc.BindableService
import io.grpc.ServerInterceptor
import io.grpc.protobuf.services.ProtoReflectionService
import io.grpc.util.TransmitStatusRuntimeExceptionInterceptor
import io.micronaut.context.annotation.Factory
import java.util.stream.Stream
import javax.inject.Singleton
import kotlin.streams.toList

@Factory
class GrpcConfig {

    @Singleton
    fun validatorInterceptor(validators: Stream<GrpcValidator<Any>>): ValidatingServerInterceptor {
        val index = validators.toList()
            .foldRight(ExplicitValidatorIndex()) { c, a -> a.add(c.payload.java, c.validator) }
        return ValidatingServerInterceptor(index)
    }

    @Singleton
    fun reflectionService(): BindableService = ProtoReflectionService.newInstance()

    @Singleton
    fun errorInterceptor(): ServerInterceptor = ReactorExceptionLogger.run { TransmitStatusRuntimeExceptionInterceptor.instance() }
}
