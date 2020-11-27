package com.github.jntakpe.commons.tracing

import com.github.jntakpe.commons.tracing.ReactorTracingProperties.Companion.TRACING_REACTOR_PREFIX
import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.context.annotation.Requires
import io.micronaut.core.bind.annotation.Bindable
import io.micronaut.core.util.StringUtils

@ConfigurationProperties(TRACING_REACTOR_PREFIX)
@Requires(property = "$TRACING_REACTOR_PREFIX.enabled", notEquals = StringUtils.FALSE)
interface ReactorTracingProperties {

    companion object {

        const val TRACING_REACTOR_PREFIX = "tracing.reactor"
    }

    @get:Bindable(defaultValue = StringUtils.FALSE)
    val decorateOnEach: Boolean
}
