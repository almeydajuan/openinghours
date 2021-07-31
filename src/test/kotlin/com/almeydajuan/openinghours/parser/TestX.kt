package com.almeydajuan.openinghours.parser

import com.almeydajuan.openinghours.Day
import com.almeydajuan.openinghours.DayProvider
import com.almeydajuan.openinghours.OpeningTime
import com.almeydajuan.openinghours.UnixTimestampConverter
import com.almeydajuan.openinghours.openingNineToEleven
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TestX {

    private val timeConverter = UnixTimestampConverter()

    @Test
    fun `Mondays open from 9 am to 11 am`() {
        assertEquals("Monday: 9 AM - 11 AM",
            parseWorkingDay(Day.MONDAY, listOf(openingNineToEleven))
        )
    }

    @Test
    fun `Mondays closed`() {
        assertEquals("Monday: 9 AM - 11 AM",
            parseWorkingDay(Day.MONDAY, listOf(openingNineToEleven))
        )
    }

    private fun parseWorkingDay(day: Day, openingTimes: List<OpeningTime>): String {
        val text = StringBuffer()
        text.append(day.output)
        text.append(": ")
        if (openingTimes.isEmpty()) {
            text.append("Closed")
        } else {
            openingTimes.forEach {
                text.append(timeConverter.convert(it.openingTime))
                text.append(" - ")
                text.append(timeConverter.convert(it.closingTime))
            }
        }
        return text.toString()
    }
}