package com.almeydajuan.openinghours

import com.almeydajuan.openinghours.validator.TIMES_ARE_INCONSISTENT
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ParsingTest {

    private val workingDayParser = WorkingWeekParser(WorkingDayParser())

    @Test
    fun `Mondays open from 9 am to 11 am`() {
        assertEquals("Monday: 9 AM - 11 AM",
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
                        transitions = nineToEleven
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
                        transitions = listOf(
                            Transition("someaction", NINE_AM_UNIX),
                            Transition(Action.OPEN.input, ELEVEN_AM_UNIX)
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
                        transitions = listOf(
                            Transition(Action.OPEN.input, ELEVEN_AM_UNIX),
                            Transition(Action.CLOSE.input, NINE_AM_UNIX)
                        )
                    )
                )
            )
        }.message

        assertEquals(TIMES_ARE_INCONSISTENT, message)
    }

    @Test
    fun `Mondays open from 9 am to 11 am and from 1 pm to 6 pm`() {
        assertEquals("Monday: 9 AM - 11 AM, 1 PM - 6 PM",
            workingDayParser.convertWorkingDays(
                listOf(
                    WorkingDay(
                        day = Day.MONDAY.input,
                        transitions = nineToEleven + oneToSix
                    )
                )
            )
        )
    }

    @Test
    fun `Mondays open from 9 am to 11 am and Tuesdays as well`() {
        assertEquals(
            """
                Monday: 9 AM - 11 AM
                Tuesday: 9 AM - 11 AM
            """.trimIndent(),
            workingDayParser.convertWorkingDays(
                listOf(
                    typicalMonday,
                    typicalMonday.copy(day = Day.TUESDAY.input)
                )
            )
        )
    }

    @Test
    fun `assert days with several times`() {
        assertEquals(
            """
                Monday: 9 AM - 11 AM, 1 PM - 6 PM
                Wednesday: 9 AM - 11 AM, 1 PM - 6 PM
            """.trimIndent(),
            workingDayParser.convertWorkingDays(
                listOf(
                    WorkingDay(
                        day = Day.MONDAY.input,
                        transitions = nineToEleven + oneToSix
                    ),
                    WorkingDay(
                        day = Day.WEDNESDAY.input,
                        transitions = nineToEleven + oneToSix
                    )
                )
            )
        )
    }

    @Test
    fun `Days without opening times are shown as closed`() {
        assertEquals(
            "Monday: Closed",
            workingDayParser.convertWorkingDays(
                listOf(
                    WorkingDay(
                        day = Day.MONDAY.input,
                        transitions = listOf()
                    )
                )
            )
        )
    }

    @Test
    @Disabled
    fun `assert take following day`() {
        assertEquals(
            """
                Monday: 9 AM - 11 AM, 1 PM - 1 AM
            """.trimIndent(),
            workingDayParser.convertWorkingDays(
                listOf(
                    WorkingDay(
                        day = Day.MONDAY.input,
                        transitions = nineToEleven + listOf(Transition(Action.OPEN.input, ONE_PM_UNIX))
                    ),
                    WorkingDay(
                        day = Day.TUESDAY.input,
                        transitions = listOf(Transition(Action.CLOSE.input, ONE_AM_UNIX))
                    )
                )
            )
        )
    }
}
