package com.github.jntakpe.commons.micronaut

import com.github.jntakpe.commons.context.ReactorTracingOperator
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Requires
import javax.inject.Singleton

@Factory
class DefaultConfig {

    @Singleton
    @Requires(missingBeans = [ReactorTracingOperator::class])
    fun noopTracingOperator(): ReactorTracingOperator = NoopReactorTracingOperator()
}
