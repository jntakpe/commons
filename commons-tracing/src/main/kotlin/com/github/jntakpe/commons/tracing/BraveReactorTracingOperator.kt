package com.github.jntakpe.commons.tracing

import brave.Tracing
import com.github.jntakpe.commons.context.ReactorTracingOperator
import org.reactivestreams.Publisher
import java.util.function.Function

class BraveReactorTracingOperator(private val tracing: Tracing) : ReactorTracingOperator {

    override fun <T> operator(): Function<in Publisher<T>, out Publisher<T>> = ReactorTracing.scopePassingSpanOperator(tracing)
}
