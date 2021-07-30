package com.almeydajuan.openinghours

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date

class TestX {

    @Test
    fun `convert 10 AM unix time to human readable format`() {
        assertEquals("10 AM", convert(32400))
    }

    @Test
    fun `convert 11 30 AM unix time to human readable format`() {
        assertEquals("11.30 AM", convert(37800))
    }

    private fun convert(unixTimestamp: Long): String {
        val date = Date.from(Instant.ofEpochSecond(unixTimestamp))
        val hourFormatter = SimpleDateFormat("HH")
        val minutesFormatter = SimpleDateFormat("mm")
        val hour = hourFormatter.format(date).toInt()
        val minutes = resolveMinutes(minutesFormatter, date)
        return "$hour$minutes AM"
    }

    private fun resolveMinutes(minutesFormatter: SimpleDateFormat, date: Date): String {
        val minutes = minutesFormatter.format(date).toString().toInt()
        return if (minutes > 0) {
            ".$minutes"
        } else {
            ""
        }
    }
}