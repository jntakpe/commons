package com.github.jntakpe.commons.micronaut

import com.github.jntakpe.commons.context.ReactorTracingOperator
import org.reactivestreams.Publisher
import java.util.function.Function

class NoopReactorTracingOperator : ReactorTracingOperator {

    override fun <T> operator(): Function<in Publisher<T>, out Publisher<T>> = Function.identity()
}
