package com.github.jntakpe.availability.config.tracing

import com.github.jntakpe.availability.config.tracing.ReactorTracingProperties.Companion.TRACING_REACTOR_PREFIX
import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.context.annotation.Requires
import io.micronaut.core.bind.annotation.Bindable
import io.micronaut.core.util.StringUtils

@ConfigurationProperties(TRACING_REACTOR_PREFIX)
@Requires(property = "$TRACING_REACTOR_PREFIX.enabled", value = StringUtils.TRUE)
interface ReactorTracingProperties {

    companion object {

        const val TRACING_REACTOR_PREFIX = "tracing.reactor"
    }

    @get:Bindable(defaultValue = StringUtils.TRUE)
    val decorateOnEach: Boolean
}
