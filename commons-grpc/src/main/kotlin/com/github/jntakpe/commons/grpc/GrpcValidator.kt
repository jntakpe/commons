package com.github.jntakpe.commons.grpc

import io.envoyproxy.pgv.ValidatorImpl
import kotlin.reflect.KClass

data class GrpcValidator<T : Any>(val payload: KClass<T>, val validator: ValidatorImpl<T>)
