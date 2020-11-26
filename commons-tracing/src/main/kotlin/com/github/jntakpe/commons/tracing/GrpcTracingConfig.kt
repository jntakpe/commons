package com.github.jntakpe.commons.tracing

import brave.Tracing
import brave.grpc.GrpcTracing
import io.grpc.ClientInterceptor
import io.grpc.ServerInterceptor
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Requires
import javax.inject.Singleton

@Factory
@Requires(classes = [ClientInterceptor::class, ServerInterceptor::class])
class GrpcTracingConfig {

    @Singleton
    fun grpcTracing(tracing: Tracing): GrpcTracing = GrpcTracing.create(tracing)

    @Singleton
    fun clientTracingInterceptor(grpcTracing: GrpcTracing): ClientInterceptor = grpcTracing.newClientInterceptor()

    @Singleton
    fun serverTracingInterceptor(grpcTracing: GrpcTracing): ServerInterceptor = grpcTracing.newServerInterceptor()
}
