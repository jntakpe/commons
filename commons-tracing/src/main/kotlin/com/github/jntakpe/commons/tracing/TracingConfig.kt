package com.github.jntakpe.commons.tracing

import brave.Tracing
import brave.context.slf4j.MDCScopeDecorator
import brave.handler.SpanHandler
import brave.propagation.ThreadLocalCurrentTraceContext
import brave.sampler.Sampler
import com.github.jntakpe.commons.context.ReactorTracingOperator
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.context.env.Environment
import io.micronaut.runtime.ApplicationConfiguration
import io.micronaut.scheduling.instrument.ReactiveInvocationInstrumenterFactory
import javax.inject.Singleton

@Factory
class TracingConfig(private val appConfig: ApplicationConfiguration) {

    @Singleton
    @Bean(preDestroy = "close")
    fun braveTracing(): Tracing {
        return Tracing.newBuilder()
            .localServiceName(appConfig.name.orElse(Environment.DEFAULT_NAME))
            .addSpanHandler(SpanHandler.NOOP)
            .sampler(Sampler.NEVER_SAMPLE)
            .currentTraceContext(ThreadLocalCurrentTraceContext.newBuilder().addScopeDecorator(MDCScopeDecorator.get()).build())
            .build()
    }

    @Singleton
    fun tracingInstrumenter(tracing: Tracing): ReactiveInvocationInstrumenterFactory = TracingInvocationInstrumenter(tracing)

    @Singleton
    fun braveTracingOperator(tracing: Tracing): ReactorTracingOperator = BraveReactorTracingOperator(tracing)
}
