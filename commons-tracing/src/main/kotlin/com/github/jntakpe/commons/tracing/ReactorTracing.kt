package com.github.jntakpe.availability.config.tracing

import brave.Tracing
import brave.propagation.CurrentTraceContext
import brave.propagation.TraceContext
import org.reactivestreams.Publisher
import reactor.core.Fuseable
import reactor.core.publisher.Operators
import reactor.util.context.Context
import java.util.function.Function

object ReactorTracing {

    fun <T> scopePassingSpanOperator(tracing: Tracing): Function<in Publisher<T>, out Publisher<T>> {
        return Operators.lift { p, sub ->
            if (p is Fuseable.ScalarCallable<*>) return@lift sub
            val context = sub.currentContext()
            val currentTraceContext = tracing.currentTraceContext()
            val parent = traceContext(context, currentTraceContext) ?: return@lift sub
            ScopePassingSpanSubscriber(sub, context, currentTraceContext, parent)
        }
    }

    private fun traceContext(context: Context, fallback: CurrentTraceContext): TraceContext? {
        return context.getOrDefault(TraceContext::class, fallback.get())
    }
}
