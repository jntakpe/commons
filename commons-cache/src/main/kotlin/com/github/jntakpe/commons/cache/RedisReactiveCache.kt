package com.github.jntakpe.commons.cache

import com.github.jntakpe.commons.context.ReactorTracingOperator
import com.github.jntakpe.commons.context.logger
import io.micronaut.cache.CacheManager
import io.micronaut.configuration.lettuce.RedisSetting
import io.micronaut.configuration.lettuce.cache.RedisCache
import io.micronaut.context.annotation.EachProperty
import io.micronaut.context.annotation.Parameter
import io.micronaut.context.annotation.Requires
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@EachProperty(RedisSetting.REDIS_CACHES)
@Requires(classes = [RedisCache::class])
class RedisReactiveCache(
    @Parameter private val cacheName: String,
    private val tracingOperator: ReactorTracingOperator,
    cacheManager: CacheManager<RedisCache>,
) {

    private val log = logger()
    private val cache = cacheManager.getCache(cacheName).async()

    fun <T> find(key: Any, type: Class<T>): Mono<T> {
        return Mono.fromFuture(cache.get(key, type))
            .doOnSubscribe { log.debug("Searching {} from cache {}", key, cacheName) }
            .transform(tracingOperator.operator())
            .flatMap { Mono.justOrEmpty(it) }
            .doOnNext { log.debug("{} retrieved from cache {} with key {}", it, cacheName, key) }
            .switchIfEmpty { log.debug("Key {} not found in cache {}", key, cacheName).run { Mono.empty() } }
            .doOnError { log.warn("Unable to retrieve {} from cache {}", key, cacheName, it) }
            .onErrorResume { Mono.empty() }
    }

    @JvmSynthetic
    inline fun <reified T> find(key: Any): Mono<T> {
        return find(key, T::class.java)
    }

    fun <K : Any, T> orPutOnCacheMiss(key: K, type: Class<T>, onCacheMiss: (K) -> Mono<T>): Mono<T> {
        return find(key, type)
            .switchIfEmpty { onCacheMiss(key).doOnNext { putAndForget(key, it) } }
    }

    @JvmSynthetic
    inline fun <K : Any, reified T> orPutOnCacheMiss(key: K, noinline onCacheMiss: (K) -> Mono<T>): Mono<T> {
        return find<T>(key)
            .switchIfEmpty { onCacheMiss(key).doOnNext { putAndForget(key, it) } }
    }

    fun <T> put(key: Any, data: T): Mono<T> {
        return Mono.fromFuture(cache.put(key, data))
            .doOnSubscribe { log.debug("Caching key {} in cache {}", key, cacheName) }
            .transform(tracingOperator.operator())
            .filter { it }
            .doOnSuccess { log.debug("Key {} cached in cache {}", key, cacheName) }
            .map { data }
            .switchIfEmpty { Mono.just(data).doOnSubscribe { log.debug("Key {} not cached in cache {}", key, cacheName) } }
            .doOnError { log.warn("Unable to cache key {} in cache {}", key, cacheName, it) }
            .onErrorReturn(data)
    }

    fun <T> putAndForget(key: Any, data: T) {
        put(key, data).subscribe()
    }

    fun evict(key: Any): Mono<Void> {
        return Mono.fromFuture(cache.invalidate(key))
            .doOnSubscribe { log.debug("Evicting {} from cache {}", key, cacheName) }
            .transform(tracingOperator.operator())
            .filter { it }
            .doOnSuccess { log.debug("{} evicted from cache {}", key, cacheName) }
            .switchIfEmpty { log.debug("Key {} not evicted from cache {}", key, cacheName).run { Mono.empty() } }
            .doOnError { log.warn("Unable to evict {} from cache {}", key, cacheName) }
            .onErrorResume { Mono.empty() }
            .then()
    }
}
