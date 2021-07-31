package com.almeydajuan.openinghours.parser

import com.almeydajuan.openinghours.Day
import com.almeydajuan.openinghours.Day.TUESDAY
import com.almeydajuan.openinghours.DayProvider
import com.almeydajuan.openinghours.ONE_AM_UNIX
import com.almeydajuan.openinghours.UnixTimestampConverter
import com.almeydajuan.openinghours.openingNineToEleven
import com.almeydajuan.openinghours.openingOneToSix
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class WeekParserTest {

    private val weekParser = WeekParser(DayParser(UnixTimestampConverter()))

    @Test
    fun `parse empty week`() {
        assertEquals("""
                Monday: Closed
                Tuesday: Closed
                Wednesday: Closed
                Thursday: Closed
                Friday: Closed
                Saturday: Closed
                Sunday: Closed
            """.trimIndent(),
            weekParser.parseWeek(emptyList())
        )
    }

    @Test
    fun `Mondays to Sunday open from 9 am to 11 am`() {
        assertEquals("""
                Monday: 9 AM - 11 AM
                Tuesday: 9 AM - 11 AM
                Wednesday: 9 AM - 11 AM
                Thursday: 9 AM - 11 AM
                Friday: 9 AM - 11 AM
                Saturday: 9 AM - 11 AM
                Sunday: 9 AM - 11 AM
            """.trimIndent(),
            weekParser.parseWeek(
                Day.values().map { DayProvider.moveOpeningToDay(it, openingNineToEleven) }
            )
        )
    }

    @Test
    fun `parse week with day going to the next one`() {
        assertEquals("""
                Monday: 9 AM - 1 AM
                Tuesday: Closed
                Wednesday: Closed
                Thursday: Closed
                Friday: Closed
                Saturday: Closed
                Sunday: Closed
            """.trimIndent(),
            weekParser.parseWeek(
                listOf(
                    openingNineToEleven.copy(
                        closingTime = DayProvider.calculateTimeForDay(TUESDAY, ONE_AM_UNIX)
                    )
                )
            )
        )
    }

    @Test
    fun `parse week with day going to the next one and the other with a schedule`() {
        assertEquals("""
                Monday: 9 AM - 1 AM
                Tuesday: 9 AM - 11 AM, 1 PM - 6 PM
                Wednesday: Closed
                Thursday: Closed
                Friday: Closed
                Saturday: Closed
                Sunday: Closed
            """.trimIndent(),
            weekParser.parseWeek(
                listOf(
                    openingNineToEleven.copy(
                        closingTime = DayProvider.calculateTimeForDay(TUESDAY, ONE_AM_UNIX)
                    ),
                    DayProvider.moveOpeningToDay(TUESDAY, openingNineToEleven),
                    DayProvider.moveOpeningToDay(TUESDAY, openingOneToSix)
                )
            )
        )
    }

}