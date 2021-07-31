package com.almeydajuan.openinghours.parser

import com.almeydajuan.openinghours.DAY_OFFSET
import com.almeydajuan.openinghours.ONE_AM_UNIX
import com.almeydajuan.openinghours.ONE_PM_UNIX
import com.almeydajuan.openinghours.OpeningTime
import com.almeydajuan.openinghours.openingNineToEleven
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OpeningTimeParserTest {

    @Test
    fun `9 AM to 11 AM`() {
        assertEquals("9 AM - 11 AM", OpeningTimeParser.parseOpeningTime(openingNineToEleven))
    }

    @Test
    fun `1 PM - 1 AM`() {
        assertEquals("1 PM - 1 AM", OpeningTimeParser.parseOpeningTime(OpeningTime(ONE_PM_UNIX, ONE_AM_UNIX + DAY_OFFSET)))
    }
}