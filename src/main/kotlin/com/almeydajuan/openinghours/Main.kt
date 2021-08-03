package com.almeydajuan.openinghours

import com.almeydajuan.openinghours.parser.DayParser
import com.almeydajuan.openinghours.parser.UnixTimestampParser
import com.almeydajuan.openinghours.parser.WeekParser
import com.almeydajuan.openinghours.provider.Day
import com.almeydajuan.openinghours.service.ParsingService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import io.netty.buffer.ByteBufInputStream
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import io.vertx.core.buffer.Buffer
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler

fun main() {
    val vertx = Vertx.vertx(VertxOptions())
    val router = Router.router(vertx)

    val parsingService = ParsingService(WeekParser(DayParser(UnixTimestampParser())))
    val objectMapper = ObjectMapper().registerModule(KotlinModule())

    router.route().handler(BodyHandler.create(false))
    router.post("/api/schedules").handler { context ->
        val body = context.body
        val bodyAsJson = body.toJsonObject()
        val workingWeek = Day.values().map { day ->
            bodyAsJson.getJsonArray(day.input)?.let { jsonArray ->
                val transitions: List<TransitionDto> = objectMapper.readValue(jsonArray.toBuffer())
                WorkingDay(day.input, transitions.map { Transition(it.type, it.value.toLong()) })
            } ?: WorkingDay(day.input, emptyList())
        }
        val parsedOpeningHours = parsingService.parseOpeningHours(workingWeek)
        context.response().setStatusCode(200).end(parsedOpeningHours)
    }

    vertx.createHttpServer()
        .requestHandler(router)
        .listen(8080)
        .onSuccess { server ->
            println("HTTP server started on port " + server.actualPort())
        }
}

inline fun <reified T> ObjectMapper.readValue(src: Buffer): T = readValue(ByteBufInputStream(src.byteBuf))

private data class TransitionDto(val type: String, val value: String)