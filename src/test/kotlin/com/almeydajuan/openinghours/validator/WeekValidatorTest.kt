package com.almeydajuan.openinghours.validator

import com.almeydajuan.openinghours.ActionParser
import com.almeydajuan.openinghours.DayParser
import com.almeydajuan.openinghours.typicalMonday
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class WeekValidatorTest {

    private val weekValidator = WeekValidator(DayValidator(DayParser(), ActionParser()))

    @Test
    fun `fail when weekday is missing`() {
        val message = assertThrows<RuntimeException> {
            weekValidator.isValid(emptyList())
        }.message

        assertEquals(DAYS_MISSING_IN_THE_WEEK, message)
    }

    @Test
    fun `fail when weekday is repeated`() {
        val message = assertThrows<RuntimeException> {
            weekValidator.isValid(listOf(typicalMonday, typicalMonday))
        }.message

        assertEquals(DAYS_ARE_REPEATED, message)
    }

    @Test
    fun `fail when weekday has a wrong format`() {
        assertThrows<RuntimeException> {
            weekValidator.isValid(listOf(
                typicalMonday.copy(day = "wrong day")
            ))
        }
    }
}

