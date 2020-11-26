package com.github.jntakpe.availability.config.tracing

import brave.Tracing
import io.micronaut.context.annotation.Requires
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.context.event.BeanCreatedEvent
import io.micronaut.context.event.BeanCreatedEventListener
import io.micronaut.context.event.ShutdownEvent
import reactor.core.publisher.Hooks
import javax.inject.Singleton

@Singleton
@Requires(beans = [ReactorTracingProperties::class])
class ReactorTracingHookRegistration(private val properties: ReactorTracingProperties) : BeanCreatedEventListener<Tracing>,
                                                                                         ApplicationEventListener<ShutdownEvent> {

    companion object {

        const val REACTOR_TRACING_HOOK_NAME = "reactor-tracing-hook"
    }

    override fun onCreated(event: BeanCreatedEvent<Tracing>): Tracing {
        return event.bean.apply { registerHooks(this) }
    }

    override fun onApplicationEvent(event: ShutdownEvent) {
        Hooks.resetOnLastOperator(REACTOR_TRACING_HOOK_NAME)
        Hooks.resetOnEachOperator(REACTOR_TRACING_HOOK_NAME)
    }

    private fun registerHooks(tracing: Tracing) {
        if (properties.decorateOnEach) Hooks.onEachOperator(REACTOR_TRACING_HOOK_NAME, ReactorTracing.scopePassingSpanOperator(tracing))
        else Hooks.onLastOperator(REACTOR_TRACING_HOOK_NAME, ReactorTracing.scopePassingSpanOperator(tracing))
    }
}
