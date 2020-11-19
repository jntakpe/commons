package com.github.jntakpe.commons.cache

import reactor.core.CoreSubscriber
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

class CacheMissMono<T>(private val delegate: Mono<T>) : Mono<T>() {

    fun onCacheMissResume(other: Mono<T>): Mono<T> = onCacheMissResume { other }

    fun onCacheMissResume(other: () -> Mono<T>): Mono<T> = switchIfEmpty { other() }

    override fun subscribe(actual: CoreSubscriber<in T>) {
        delegate.subscribe(actual)
    }
}
