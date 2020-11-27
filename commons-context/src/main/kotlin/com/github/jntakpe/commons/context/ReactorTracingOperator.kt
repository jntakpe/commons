package com.github.jntakpe.commons.context

import org.reactivestreams.Publisher
import java.util.function.Function

interface ReactorTracingOperator {

    fun <T> operator(): Function<in Publisher<T>, out Publisher<T>>
}
