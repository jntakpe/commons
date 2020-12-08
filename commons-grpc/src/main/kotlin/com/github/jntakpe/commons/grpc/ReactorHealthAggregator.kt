package com.github.jntakpe.commons.grpc

import io.micronaut.management.endpoint.health.HealthLevelOfDetail
import io.micronaut.management.health.aggregator.HealthAggregator
import io.micronaut.management.health.aggregator.RxJavaHealthAggregator
import io.micronaut.management.health.indicator.HealthIndicator
import io.micronaut.management.health.indicator.HealthResult
import io.micronaut.runtime.ApplicationConfiguration
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

class ReactorHealthAggregator(applicationConfiguration: ApplicationConfiguration) : HealthAggregator<HealthResult> {

    private val delegate = RxJavaHealthAggregator(applicationConfiguration)

    override fun aggregate(indicators: Array<out HealthIndicator>, healthLevelOfDetail: HealthLevelOfDetail): Mono<HealthResult> {
        return delegate.aggregate(indicators, healthLevelOfDetail).toMono()
    }

    override fun aggregate(name: String, results: Publisher<HealthResult>): Mono<HealthResult> {
        return delegate.aggregate(name, results).toMono()
    }
}
