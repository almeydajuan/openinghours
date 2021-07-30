package com.almeydajuan.openinghours

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.RuntimeException

class TestXX {

    private val timeConverter = UnixTimestampConverter()

    @Test
    fun `Mondays open from 9 am to 6 pm`() {
        assertEquals("Monday from 9 AM to 6 PM", convert("Monday", "open", NINE_AM_UNIX, "close", SIX_PM_UNIX))
    }

    @Test
    fun `Mondays open from 9 am to 11 am`() {
        assertEquals("Monday from 9 AM to 11 AM", convert("Monday", "open", NINE_AM_UNIX, "close", ELEVEN_AM_UNIX))
    }

    @Test
    fun `fail when day is not accepted`() {
        val message = assertThrows<RuntimeException> {
            convert("Someday", "open", NINE_AM_UNIX, "close", ELEVEN_AM_UNIX)
        }.message

        assertEquals(DAY_NOT_SUPPORTED, message)
    }

    @Test
    fun `fail when action is not accepted`() {
        val message = assertThrows<RuntimeException> {
            convert("Monday", "action", NINE_AM_UNIX, "close", ELEVEN_AM_UNIX)
        }.message

        assertEquals(ACTION_NOT_SUPPORTED, message)
    }

    private fun convert(day: String, action: String, time1: Long, action2: String, time2: Long): String {
        validateInput(day, action)

        return "${Day.valueOf(day)} from ${timeConverter.convert(time1)} to ${timeConverter.convert(time2)}"
    }

    private fun validateInput(day: String, action: String) {
        if (!Day.values().map { it.name }.contains(day)) {
            throw RuntimeException(DAY_NOT_SUPPORTED)
        }
        if (!Action.values().map { it.name }.contains(action)) {
            throw RuntimeException(ACTION_NOT_SUPPORTED)
        }
    }
}

enum class Day(name: String) {
    Monday("monday"),
    Tuesday("tuesday"),
    Wednesday("wednesday"),
    Thursday("thursday"),
    Friday("friday"),
    Saturday("saturday"),
    Sunday("sunday")
}

enum class Action(name: String) {
    OPEN("open"),
    CLOSE("close")
}

const val DAY_NOT_SUPPORTED = "Day is not supported"
const val ACTION_NOT_SUPPORTED = "Action is not supported"
