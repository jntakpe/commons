package com.github.jntakpe.commons.context

import io.grpc.Metadata
import io.grpc.Status
import io.grpc.StatusRuntimeException

class CommonException(
    override val message: String,
    val logging: (String, Throwable) -> Unit,
    code: Status.Code,
    cause: Throwable? = null,
    metadata: Metadata? = null
) : StatusRuntimeException(Status.fromCode(code).withDescription(message).withCause(cause), metadata)
