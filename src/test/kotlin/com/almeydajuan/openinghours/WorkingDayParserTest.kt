package com.almeydajuan.openinghours

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class WorkingDayParserTest {

    private val workingDayParser = WorkingDayParser(
        timeConverter = UnixTimestampConverter(),
        dayParser = DayParser(),
        actionParser = ActionParser()
    )

    @Test
    fun `Mondays open from 9 am to 11 am`() {
        assertEquals("Monday 9 AM - 11 AM",
            workingDayParser.convertWorkingDays(
                listOf(typicalMonday)
            )
        )
    }

    @Test
    fun `fail when day is not accepted`() {
        val message = assertThrows<RuntimeException> {
            workingDayParser.convertWorkingDays(
                listOf(
                    WorkingDay(
                        day = "someday",
                        actions = nineToEleven
                    )
                )
            )
        }.message

        assertEquals(DAY_NOT_SUPPORTED, message)
    }

    @Test
    fun `fail when action is not accepted`() {
        val message = assertThrows<RuntimeException> {
            workingDayParser.convertWorkingDays(
                listOf(
                    WorkingDay(
                        day = Day.MONDAY.input,
                        actions = listOf(
                            DayAction("someaction", NINE_AM_UNIX),
                            DayAction(Action.OPEN.input, ELEVEN_AM_UNIX)
                        )
                    )
                )
            )
        }.message

        assertEquals(ACTION_NOT_SUPPORTED, message)
    }

    @Test
    fun `fail when time is inconsistent`() {
        val message = assertThrows<RuntimeException> {
            workingDayParser.convertWorkingDays(
                listOf(
                    WorkingDay(
                        day = Day.MONDAY.input,
                        actions = listOf(
                            DayAction(Action.OPEN.input, ELEVEN_AM_UNIX),
                            DayAction(Action.CLOSE.input, NINE_AM_UNIX)
                        )
                    )
                )
            )
        }.message

        assertEquals(TIMES_ARE_INCONSISTENT, message)
    }

    @Test
    fun `Mondays open from 9 am to 11 am and from 1 pm to 6 pm`() {
        assertEquals("Monday 9 AM - 11 AM, 1 PM - 6 PM",
            workingDayParser.convertWorkingDays(
                listOf(
                    WorkingDay(
                        day = Day.MONDAY.input,
                        actions = nineToEleven + oneToSix
                    )
                )
            )
        )
    }

    @Test
    fun `Mondays open from 9 am to 11 am and Tuesdays as well`() {
        assertEquals(
            """
                Monday 9 AM - 11 AM
                Tuesday 9 AM - 11 AM
            """.trimIndent(),
            workingDayParser.convertWorkingDays(
                listOf(
                    WorkingDay(
                        day = Day.MONDAY.input,
                        actions = nineToEleven
                    ),
                    WorkingDay(
                        day = Day.TUESDAY.input,
                        actions = nineToEleven
                    )
                )
            )
        )
    }

    @Test
    fun `assert more complex days`() {
        assertEquals(
            """
                Monday 9 AM - 11 AM, 1 PM - 6 PM
                Wednesday 9 AM - 11 AM, 1 PM - 6 PM
            """.trimIndent(),
            workingDayParser.convertWorkingDays(
                listOf(
                    WorkingDay(
                        day = Day.MONDAY.input,
                        actions = nineToEleven + oneToSix
                    ),
                    WorkingDay(
                        day = Day.WEDNESDAY.input,
                        actions = nineToEleven + oneToSix
                    )
                )
            )
        )
    }
}
