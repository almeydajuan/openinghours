package com.almeydajuan.openinghours

import com.almeydajuan.openinghours.provider.Day
import com.almeydajuan.openinghours.service.ParsingService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.netty.buffer.ByteBufInputStream
import io.vertx.core.buffer.Buffer
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler

class SchedulesController(
    private val objectMapper: ObjectMapper,
    private val parsingService: ParsingService
) {
    fun installRoute(router: Router) {
        router.route().handler(BodyHandler.create(false))
        router.post(API_SCHEDULES).handler { ctx ->
            val body = ctx.body
            val bodyAsJson = body.toJsonObject()
            val workingWeek = Day.values().map { day ->
                bodyAsJson.getJsonArray(day.input)?.let { jsonArray ->
                    val transitions: List<TransitionDto> = objectMapper.readValue(jsonArray.toBuffer())
                    WorkingDay(day.input, transitions.map { Transition(it.type, it.value.toLong()) })
                } ?: WorkingDay(day.input, emptyList())
            }
            val parsedOpeningHours = parsingService.parseOpeningHours(workingWeek)
            ctx.response().setStatusCode(200).end(parsedOpeningHours)
        }
    }
}

inline fun <reified T> ObjectMapper.readValue(src: Buffer): T = readValue(ByteBufInputStream(src.byteBuf))

private data class TransitionDto(val type: String, val value: String)

const val API_SCHEDULES = "/api/schedules"