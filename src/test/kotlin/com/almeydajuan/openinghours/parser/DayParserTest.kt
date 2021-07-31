package com.almeydajuan.openinghours.parser

import com.almeydajuan.openinghours.Day.MONDAY
import com.almeydajuan.openinghours.UnixTimestampConverter
import com.almeydajuan.openinghours.openingNineToEleven
import com.almeydajuan.openinghours.openingOneToSix
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DayParserTest {

    private val dayParser = DayParser(UnixTimestampConverter())

    @Test
    fun `Mondays open from 9 am to 11 am`() {
        assertEquals("Monday: 9 AM - 11 AM",
            dayParser.parseWorkingDay(MONDAY, listOf(openingNineToEleven))
        )
    }

    @Test
    fun `Mondays closed`() {
        assertEquals("Monday: Closed",
            dayParser.parseWorkingDay(MONDAY, emptyList())
        )
    }

    @Test
    fun `Mondays open from 9 am to 11 am and from 1 pm to 6 pm`() {
        assertEquals("Monday: 9 AM - 11 AM, 1 PM - 6 PM",
            dayParser.parseWorkingDay(MONDAY, listOf(openingNineToEleven, openingOneToSix))
        )
    }
}