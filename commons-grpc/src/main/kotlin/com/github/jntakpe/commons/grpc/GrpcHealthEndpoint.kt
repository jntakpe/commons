package com.github.jntakpe.commons.grpc

import io.grpc.health.v1.HealthCheckRequest
import io.grpc.health.v1.HealthCheckResponse
import io.grpc.health.v1.ReactorHealthGrpc
import io.micronaut.health.HealthStatus
import io.micronaut.management.endpoint.health.HealthLevelOfDetail
import io.micronaut.management.health.indicator.HealthIndicator
import io.micronaut.management.health.indicator.HealthResult
import reactor.core.publisher.Mono

class GrpcHealthEndpoint(private val aggregator: ReactorHealthAggregator, private val indicators: Array<HealthIndicator>) :
    ReactorHealthGrpc.HealthImplBase() {

    override fun check(request: Mono<HealthCheckRequest>): Mono<HealthCheckResponse> {
        return aggregator.aggregate(indicators, HealthLevelOfDetail.STATUS)
            .map { HealthCheckResponse.newBuilder().setStatus(it.toGrpc()).build() }
    }

    private fun HealthResult.toGrpc() = when (status) {
        HealthStatus.UP -> HealthCheckResponse.ServingStatus.SERVING
        HealthStatus.DOWN -> HealthCheckResponse.ServingStatus.NOT_SERVING
        else -> HealthCheckResponse.ServingStatus.UNKNOWN
    }
}
