package com.github.jntakpe.commons.cache

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
class RedisReactiveCache(@Parameter private val cacheName: String, cacheManager: CacheManager<RedisCache>) {

    private val log = logger()
    private val cache = cacheManager.getCache(cacheName).async()

    fun <T> find(key: Any, type: Class<T>): CacheMissMono<T> {
        return Mono.fromFuture(cache.get(key, type))
            .doOnSubscribe { log.debug("Searching {} from cache {}", key, cacheName) }
            .flatMap { Mono.justOrEmpty(it) }
            .doOnNext { log.debug("{} retrieved from cache {} with key {}", it, cacheName, key) }
            .switchIfEmpty { log.debug("Key {} not found in {}", key, cacheName).run { Mono.empty() } }
            .doOnError { log.warn("Unable to retrieve {} from cache {}", key, cacheName, it) }
            .onErrorResume { Mono.empty() }
            .let { CacheMissMono(it) }
    }

    @JvmSynthetic
    inline fun <reified T : Any> find(key: Any): CacheMissMono<T> {
        return find(key, T::class.java)
    }

    fun <T> put(key: Any, data: T): Mono<T> {
        return Mono.fromFuture(cache.put(key, data))
            .doOnSubscribe { log.debug("Caching key {} in {}", key, cacheName) }
            .filter { it }
            .doOnSuccess { log.debug("Key {} cached in {}", key, cacheName) }
            .map { data }
            .switchIfEmpty { Mono.just(data).doOnSubscribe { log.debug("Key {} not cached in {}", key, cacheName) } }
            .doOnError { log.warn("Unable to cache key {} in {}", key, cacheName, it) }
            .onErrorReturn(data)
    }

    fun <T> putAndForget(key: Any, data: T) {
        put(key, data).subscribe()
    }

    fun evict(key: Any): Mono<Void> {
        return Mono.fromFuture(cache.invalidate(key))
            .doOnSubscribe { log.debug("Evicting {} from {}", key, cacheName) }
            .filter { it }
            .doOnSuccess { log.debug("{} evicted from {}", key, cacheName) }
            .switchIfEmpty { log.debug("Key {} not evicted from {}", key, cacheName).run { Mono.empty() } }
            .doOnError { log.warn("Unable to evict {} from {}", key, cacheName) }
            .onErrorResume { Mono.empty() }
            .then()
    }
}
