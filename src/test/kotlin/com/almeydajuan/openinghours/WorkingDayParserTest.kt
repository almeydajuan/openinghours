package com.almeydajuan.openinghours

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.RuntimeException

class WorkingDayParserTest {

    private val workingDayParser = WorkingDayParser(
        timeConverter = UnixTimestampConverter(),
        dayParser = DayParser(),
        actionParser = ActionParser()
    )

    @Test
    fun `Mondays open from 9 am to 6 pm`() {
        assertEquals("Monday from 9 AM to 6 PM", workingDayParser.convert("monday", "open", NINE_AM_UNIX, "close", SIX_PM_UNIX))
    }

    @Test
    fun `Mondays open from 9 am to 11 am`() {
        assertEquals("Monday from 9 AM to 11 AM", workingDayParser.convert("monday", "open", NINE_AM_UNIX, "close", ELEVEN_AM_UNIX))
    }

    @Test
    fun `fail when day is not accepted`() {
        val message = assertThrows<RuntimeException> {
            workingDayParser.convert("Someday", "open", NINE_AM_UNIX, "close", ELEVEN_AM_UNIX)
        }.message

        assertEquals(DAY_NOT_SUPPORTED, message)
    }

    @Test
    fun `fail when action is not accepted`() {
        val message = assertThrows<RuntimeException> {
            workingDayParser.convert("monday", "action", NINE_AM_UNIX, "close", ELEVEN_AM_UNIX)
        }.message

        assertEquals(ACTION_NOT_SUPPORTED, message)
    }
}
