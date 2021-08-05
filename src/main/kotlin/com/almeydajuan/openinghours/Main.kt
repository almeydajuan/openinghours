package com.almeydajuan.openinghours

import com.almeydajuan.openinghours.parser.DayParser
import com.almeydajuan.openinghours.parser.UnixTimestampParser
import com.almeydajuan.openinghours.parser.WeekParser
import com.almeydajuan.openinghours.service.ParsingService
import com.almeydajuan.openinghours.service.WeekScheduleRepository
import com.almeydajuan.openinghours.web.installErrorHandler
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import io.vertx.ext.web.Router

fun main() {
    val vertx = Vertx.vertx(VertxOptions())

    val weekParser = WeekParser(DayParser(UnixTimestampParser()))
    val weekScheduleRepository = WeekScheduleRepository()
    val parsingService = ParsingService(weekParser, weekScheduleRepository)
    val objectMapper = createObjectMapper()
    val schedulesController = SchedulesController(objectMapper, parsingService)

    val router = Router.router(vertx)
        .also { schedulesController.installRoute(it) }
        .installErrorHandler(objectMapper)

    vertx.createHttpServer()
        .requestHandler(router)
        .listen(8080)
        .onSuccess { server ->
            println("HTTP server started on port " + server.actualPort())
        }
}

fun createObjectMapper(): ObjectMapper = ObjectMapper().registerModule(KotlinModule())
