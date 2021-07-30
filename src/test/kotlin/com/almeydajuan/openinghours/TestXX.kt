package com.almeydajuan.openinghours

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TestXX {

    @Test
    fun `Mondays open from 9 am to 6 pm`() {
        assertEquals("Monday from 9 AM to 6 PM", convert("Monday", "open", NINE_AM_UNIX, "close", SIX_PM_UNIX))
    }

    private fun convert(day: String, action: String, time1: Long, action2: String, time2: Long): String {
        return "Monday from 9 AM to 6 PM"
    }
}