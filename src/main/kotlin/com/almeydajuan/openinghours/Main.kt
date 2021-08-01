package com.almeydajuan.openinghours

import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import io.vertx.ext.web.Router
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj

fun main() {
    val vertx = Vertx.vertx(VertxOptions())
    val router = Router.router(vertx)

    router.route().handler { context ->
        val address = context.request().connection().remoteAddress().toString()
        val queryParams = context.queryParams()
        val name = queryParams.get("name") ?: "unknown"
        context.json(
            json {
                obj(
                    "name" to name,
                    "address" to address,
                    "message" to "Hello $name connected from $address"
                )
            }
        )
    }

    vertx.createHttpServer()
        .requestHandler(router)
        .listen(8080)
        .onSuccess { server ->
            println("HTTP server started on port " + server.actualPort())
        }
}