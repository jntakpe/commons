package com.github.jntakpe.commons.grpc

import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Requires
import io.micronaut.management.health.indicator.HealthIndicator
import io.micronaut.runtime.ApplicationConfiguration
import javax.inject.Singleton

@Factory
@Requires(classes = [HealthIndicator::class])
class GrpcHealthConfig {

    @Singleton
    fun healthAggregator(appConfiguration: ApplicationConfiguration) = ReactorHealthAggregator(appConfiguration)

    @Singleton
    fun healthEndpoint(aggregator: ReactorHealthAggregator, indicators: Array<HealthIndicator>, mapper: ObjectMapper): GrpcHealthEndpoint {
        return GrpcHealthEndpoint(aggregator, indicators, mapper)
    }
}
