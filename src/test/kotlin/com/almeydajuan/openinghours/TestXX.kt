package com.almeydajuan.openinghours

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.lang.StringBuilder

class TestXX {

    @Test
    fun `Mondays open from 9 am to 11 am`() {
        assertEquals("9 AM - 11 AM", parseOpeningTime(openingNineToEleven))
    }

    private fun parseOpeningTime(openingTime: OpeningTime): String {
        val text = StringBuilder()
        text.append(UnixTimestampConverter.convert(openingTime.openingTime))
        text.append(" - ")
        text.append(UnixTimestampConverter.convert(openingTime.closingTime))
        return text.toString()
    }
}