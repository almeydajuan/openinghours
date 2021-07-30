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
        assertEquals("Monday 9 AM - 6 PM",
            workingDayParser.convert(
                day = "monday",
                actions = listOf(DayAction("open", NINE_AM_UNIX), DayAction("close", SIX_PM_UNIX))
            )
        )
    }

    @Test
    fun `Mondays open from 9 am to 11 am`() {
        assertEquals("Monday 9 AM - 11 AM",
            workingDayParser.convert(
                day = "monday",
                actions = listOf(DayAction("open", NINE_AM_UNIX), DayAction("close", ELEVEN_AM_UNIX))
            )
        )
    }

    @Test
    fun `fail when day is not accepted`() {
        val message = assertThrows<RuntimeException> {
            workingDayParser.convert(
                day = "someday",
                actions = listOf(DayAction("open", NINE_AM_UNIX), DayAction("close", ELEVEN_AM_UNIX))
            )
        }.message

        assertEquals(DAY_NOT_SUPPORTED, message)
    }

    @Test
    fun `fail when action is not accepted`() {
        val message = assertThrows<RuntimeException> {
            workingDayParser.convert(
                day = "monday",
                actions = listOf(DayAction("someaction", NINE_AM_UNIX), DayAction("close", ELEVEN_AM_UNIX))
            )
        }.message

        assertEquals(ACTION_NOT_SUPPORTED, message)
    }

    @Test
    fun `Mondays open from 9 am to 11 am and from 1 pm to 6 pm`() {
        assertEquals("Monday 9 AM - 11 AM, 1 PM - 6 PM",
            workingDayParser.convert(
                day = "monday",
                actions = listOf(DayAction("open", NINE_AM_UNIX), DayAction("close", ELEVEN_AM_UNIX))
            )
        )
    }
}
