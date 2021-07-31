package com.almeydajuan.openinghours

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UnixTimestampConverterTest {

    private val timeConverter = UnixTimestampConverter()

    @Test
    fun `convert 9 AM unix time to human readable format`() {
        assertEquals("9 AM", timeConverter.convert(NINE_AM_UNIX))
    }

    @Test
    fun `convert 10 30 AM unix time to human readable format`() {
        assertEquals("10.30 AM", timeConverter.convert(37800))
    }

    @Test
    fun `convert 11 59 PM unix time to human readable format`() {
        assertEquals("11.59 PM", timeConverter.convert(86399))
    }
}