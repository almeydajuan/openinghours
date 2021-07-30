package com.almeydajuan.openinghours

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UnixTimestampConverterTest {

    private val converter = UnixTimestampConverter()

    @Test
    fun `convert 9 AM unix time to human readable format`() {
        assertEquals("9 AM", converter.convert(32400))
    }

    @Test
    fun `convert 10 30 AM unix time to human readable format`() {
        assertEquals("10.30 AM", converter.convert(37800))
    }

    @Test
    fun `convert 11 59 PM unix time to human readable format`() {
        assertEquals("11.59 PM", converter.convert(86399))
    }

    @Test
    fun `fail when underflow value`() {
        val message = assertThrows<RuntimeException> {
            converter.convert(-1)
        }.message

        assertEquals(OUT_OF_RANGE_DATE, message)
    }

    @Test
    fun `fail when overflow value`() {
        val message = assertThrows<RuntimeException> {
            converter.convert(86400)
        }.message

        assertEquals(OUT_OF_RANGE_DATE, message)
    }
}