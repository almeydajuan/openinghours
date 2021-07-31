package com.almeydajuan.openinghours.validator

import com.almeydajuan.openinghours.Day
import com.almeydajuan.openinghours.typicalMonday
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class WeekValidatorTest {

    @Test
    fun `fail when weekday is empty`() {
        val message = assertThrows<RuntimeException> {
            WeekValidator.isValid(emptyList())
        }.message

        assertEquals(EMPTY_WEEK, message)
    }

    @Test
    fun `fail when weekday is repeated`() {
        val message = assertThrows<RuntimeException> {
            WeekValidator.isValid(listOf(typicalMonday, typicalMonday))
        }.message

        assertEquals(DAYS_ARE_REPEATED, message)
    }

    @Test
    fun `fail when weekday has a wrong format`() {
        assertThrows<RuntimeException> {
            WeekValidator.isValid(listOf(
                typicalMonday.copy(day = "wrong day")
            ))
        }
    }

    @Test
    fun `accept week with valid days`() {
        assertTrue(WeekValidator.isValid(listOf(typicalMonday, typicalMonday.copy(day = Day.WEDNESDAY.input))))
    }
}

