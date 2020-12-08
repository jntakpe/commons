package com.github.jntakpe.commons.grpc

import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Requires
import io.micronaut.management.health.indicator.HealthIndicator
import io.micronaut.runtime.ApplicationConfiguration
import javax.inject.Singleton

@Factory
@Requires(classes = [HealthIndicator::class])
class GrpcHealthConfig {

    @Singleton
    fun healthAggregator(applicationConfiguration: ApplicationConfiguration) = ReactorHealthAggregator(applicationConfiguration)

    @Singleton
    fun healthEndpoint(aggregator: ReactorHealthAggregator, indicators: Array<HealthIndicator>): GrpcHealthEndpoint {
        return GrpcHealthEndpoint(aggregator, indicators)
    }
}
