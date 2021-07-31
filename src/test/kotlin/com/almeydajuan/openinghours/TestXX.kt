package com.almeydajuan.openinghours

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.lang.StringBuilder

class TestXX {

    @Test
    fun `9 AM to 11 AM`() {
        assertEquals("9 AM - 11 AM", parseOpeningTime(openingNineToEleven))
    }

    @Test
    fun `1 PM - 1 AM`() {
        assertEquals("1 PM - 1 AM", parseOpeningTime(OpeningTime(ONE_PM_UNIX, ONE_AM_UNIX + DAY_OFFSET)))
    }

    private fun parseOpeningTime(openingTime: OpeningTime): String {
        val text = StringBuilder()
        text.append(UnixTimestampConverter.convert(openingTime.openingTime))
        text.append(" - ")
        text.append(UnixTimestampConverter.convert(openingTime.closingTime))
        return text.toString()
    }
}