package com.almeydajuan.openinghours

import com.almeydajuan.openinghours.validator.OUT_OF_RANGE_DATE
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UnixTimestampConverterTest {

    @Test
    fun `convert 9 AM unix time to human readable format`() {
        assertEquals("9 AM", UnixTimestampConverter.convert(NINE_AM_UNIX))
    }

    @Test
    fun `convert 10 30 AM unix time to human readable format`() {
        assertEquals("10.30 AM", UnixTimestampConverter.convert(37800))
    }

    @Test
    fun `convert 11 59 PM unix time to human readable format`() {
        assertEquals("11.59 PM", UnixTimestampConverter.convert(86399))
    }

    @Test
    fun `fail when underflow value`() {
        val message = assertThrows<RuntimeException> {
            UnixTimestampConverter.convert(-1)
        }.message

        assertEquals(OUT_OF_RANGE_DATE, message)
    }

    @Test
    fun `fail when overflow value`() {
        val message = assertThrows<RuntimeException> {
            UnixTimestampConverter.convert(86400)
        }.message

        assertEquals(OUT_OF_RANGE_DATE, message)
    }
}