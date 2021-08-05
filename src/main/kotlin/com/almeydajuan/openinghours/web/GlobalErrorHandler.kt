package com.almeydajuan.openinghours.web

import com.fasterxml.jackson.databind.ObjectMapper
import io.netty.handler.codec.http.HttpResponseStatus
import io.vertx.core.buffer.Buffer
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext

fun Router.installErrorHandler(objectMapper: ObjectMapper): Router {
    errorHandler(HttpResponseStatus.INTERNAL_SERVER_ERROR.code()) { ctx ->
        sendError(
            ctx = ctx,
            objectMapper = objectMapper,
            title = HttpResponseStatus.INTERNAL_SERVER_ERROR.reasonPhrase(),
            status = HttpResponseStatus.INTERNAL_SERVER_ERROR.code(),
            detail = ctx.failure().message
        )
    }

    errorHandler(HttpResponseStatus.BAD_REQUEST.code()) { ctx ->
        sendError(
            ctx = ctx,
            objectMapper = objectMapper,
            title = HttpResponseStatus.BAD_REQUEST.reasonPhrase(),
            status = HttpResponseStatus.BAD_REQUEST.code(),
            detail = ctx.failure().message
        )
    }

    return this
}

private fun sendError(ctx: RoutingContext, objectMapper: ObjectMapper, title: String, status: Int, detail: String?) {
    val body = ExceptionBody(
        title = title,
        status = status,
        detail = detail
    )
    ctx.response()
        .setStatusCode(body.status)
        .putHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON)
        .end(Buffer.buffer(objectMapper.writeValueAsBytes(body)))
}

private data class ExceptionBody(
    val title: String,
    val status: Int,
    val detail: String?,
)

private const val HEADER_CONTENT_TYPE = "content-type"
private const val CONTENT_TYPE_JSON = "application/json"
