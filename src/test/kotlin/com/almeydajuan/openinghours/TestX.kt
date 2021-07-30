package com.almeydajuan.openinghours

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TestX {

    @Test
    fun `convert unix time to human readable format`() {
        assertEquals("9 AM", convert(32400))
    }

    private fun convert(unixTimestamp: Long): String {
        return "9 AM"
    }
}