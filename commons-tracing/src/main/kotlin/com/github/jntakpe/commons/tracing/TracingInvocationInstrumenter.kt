package com.github.jntakpe.commons.tracing

import brave.Tracing
import io.micronaut.scheduling.instrument.Instrumentation
import io.micronaut.scheduling.instrument.InvocationInstrumenter
import io.micronaut.scheduling.instrument.ReactiveInvocationInstrumenterFactory

class TracingInvocationInstrumenter(tracing: Tracing) : ReactiveInvocationInstrumenterFactory {

    private val currentTraceContext = tracing.currentTraceContext()

    override fun newReactiveInvocationInstrumenter(): InvocationInstrumenter? {
        return currentTraceContext.get()
            ?.run { InvocationInstrumenter { Instrumentation { currentTraceContext.maybeScope(this).close() } } }
    }
}
