package com.almeydajuan.openinghours.integration

import com.almeydajuan.openinghours.API_SCHEDULES
import com.almeydajuan.openinghours.BASE_URL
import com.almeydajuan.openinghours.SchedulesController
import com.almeydajuan.openinghours.TEST_PORT
import com.almeydajuan.openinghours.createObjectMapper
import com.almeydajuan.openinghours.mondayBody
import com.almeydajuan.openinghours.mondayResponse
import com.almeydajuan.openinghours.parser.DayParser
import com.almeydajuan.openinghours.parser.UnixTimestampParser
import com.almeydajuan.openinghours.parser.WeekParser
import com.almeydajuan.openinghours.service.ParsingService
import io.netty.handler.codec.http.HttpResponseStatus.OK
import io.vertx.core.Vertx
import io.vertx.core.buffer.Buffer
import io.vertx.ext.web.Router
import io.vertx.ext.web.client.WebClient
import io.vertx.junit5.VertxExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(VertxExtension::class)
@Disabled("currently not working")
class SchedulesTest {

    private val schedulesController = SchedulesController(
        objectMapper = createObjectMapper(),
        parsingService = ParsingService(WeekParser(DayParser(UnixTimestampParser())))
    )

    @BeforeAll
    fun setup(vertx: Vertx) {
        val router = Router.router(vertx).also { schedulesController.installRoute(it) }
        vertx.createHttpServer().requestHandler(router).listen(TEST_PORT)
    }

    @Test
    fun `process monday schedule`(vertx: Vertx) {
        val webClient = WebClient.create(vertx)
        val response = webClient.postAbs("$BASE_URL$API_SCHEDULES")
            .sendBuffer(Buffer.buffer(mondayBody)).result()

        assertEquals(OK.code(), response.statusCode())
        assertEquals(mondayResponse.trimIndent(), response.body())
    }
}