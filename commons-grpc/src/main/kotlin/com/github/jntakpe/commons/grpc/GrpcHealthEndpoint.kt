package com.github.jntakpe.commons.grpc

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.jntakpe.commons.context.logger
import io.grpc.health.v1.HealthCheckRequest
import io.grpc.health.v1.HealthCheckResponse
import io.grpc.health.v1.ReactorHealthGrpc
import io.micronaut.health.HealthStatus
import io.micronaut.management.endpoint.health.HealthLevelOfDetail
import io.micronaut.management.health.indicator.HealthIndicator
import io.micronaut.management.health.indicator.HealthResult
import reactor.core.publisher.Mono

class GrpcHealthEndpoint(
    private val aggregator: ReactorHealthAggregator,
    private val indicators: Array<HealthIndicator>,
    private val mapper: ObjectMapper,
) :
    ReactorHealthGrpc.HealthImplBase() {

    private val log = logger()

    override fun check(request: Mono<HealthCheckRequest>): Mono<HealthCheckResponse> {
        return aggregator.aggregate(indicators, HealthLevelOfDetail.STATUS_DESCRIPTION_DETAILS)
            .doOnNext { it.logHealthCheck() }
            .map { HealthCheckResponse.newBuilder().setStatus(it.toGrpc()).build() }
    }

    private fun HealthResult.toGrpc() = when (status) {
        HealthStatus.UP -> HealthCheckResponse.ServingStatus.SERVING
        HealthStatus.DOWN -> HealthCheckResponse.ServingStatus.NOT_SERVING
        else -> HealthCheckResponse.ServingStatus.UNKNOWN
    }

    private fun HealthResult.logHealthCheck() {
        when (status) {
            HealthStatus.UP -> if (log.isTraceEnabled) log.trace("Health check succeeded: ${log()}")
            HealthStatus.DOWN -> if (log.isDebugEnabled) log.debug("Health check failed: ${log()}")
            else -> log.info("Health check status unknown: ${log()}")
        }
    }

    private fun HealthResult.log() = mapper.writeValueAsString(this)
}
