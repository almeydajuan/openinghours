package com.almeydajuan.openinghours.validator

import com.almeydajuan.openinghours.Action
import com.almeydajuan.openinghours.Day
import com.almeydajuan.openinghours.ONE_PM_UNIX
import com.almeydajuan.openinghours.SIX_PM_UNIX
import com.almeydajuan.openinghours.Transition
import com.almeydajuan.openinghours.nineToEleven
import com.almeydajuan.openinghours.typicalMonday
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class WeekValidatorTest {

    @Test
    fun `accept week with valid days`() {
        assertTrue(WeekValidator.isValid(listOf(typicalMonday, typicalMonday.copy(day = Day.WEDNESDAY.input))))
    }

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
    fun `fail when inconsistent open and closing times`() {
        val message = assertThrows<RuntimeException> {
            WeekValidator.isValid(listOf(typicalMonday.copy(
                transitions = listOf(
                    Transition(Action.CLOSE.input, ONE_PM_UNIX),
                    Transition(Action.OPEN.input, SIX_PM_UNIX),
                )
            )))
        }.message

        assertEquals(INCONSISTENT_ORDER_OPEN_CLOSE_TIMES, message)
    }

    @Test
    fun `unfinished opening times cannot be processed`() {
        val message = assertThrows<RuntimeException> {
            WeekValidator.isValid(listOf(typicalMonday.copy(transitions = listOf(nineToEleven.first()))))
        }.message

        assertEquals(TIMES_ARE_INCONSISTENT, message)
    }
}

