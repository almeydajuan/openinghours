package com.almeydajuan.openinghours

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.TimeZone

class TestX {

    @Test
    fun `convert 9 AM unix time to human readable format`() {
        assertEquals("9 AM", convert(32400))
    }

    @Test
    fun `convert 10 30 AM unix time to human readable format`() {
        assertEquals("10.30 AM", convert(37800))
    }

    @Test
    fun `convert 11 59 PM unix time to human readable format`() {
        assertEquals("11.59 PM", convert(86399))
    }

    private fun convert(unixTimestamp: Long): String {
        val date = Date.from(Instant.ofEpochSecond(unixTimestamp))
        val hourFormatter = SimpleDateFormat("HH").apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        val minutesFormatter = SimpleDateFormat("mm")
        val hour = hourFormatter.format(date).toInt()
        val minutes = minutesFormatter.format(date).toString().toInt()

        val hourText = if (hour > 12) hour-12 else hour
        val minutesText = if (minutes > 0 ) ".$minutes" else ""
        val meridiem = if (hour >= 12) "PM" else "AM"

        return "$hourText$minutesText $meridiem"
    }
}