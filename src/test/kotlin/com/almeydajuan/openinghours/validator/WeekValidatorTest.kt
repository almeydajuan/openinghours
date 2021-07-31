package com.almeydajuan.openinghours.validator

import com.almeydajuan.openinghours.ONE_PM_UNIX
import com.almeydajuan.openinghours.SIX_PM_UNIX
import com.almeydajuan.openinghours.Transition
import com.almeydajuan.openinghours.nineToEleven
import com.almeydajuan.openinghours.provider.Action.CLOSE
import com.almeydajuan.openinghours.provider.Action.OPEN
import com.almeydajuan.openinghours.provider.Day.WEDNESDAY
import com.almeydajuan.openinghours.typicalMonday
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class WeekValidatorTest {

    @Test
    fun `accept week with valid days`() {
        WeekValidator.validateWorkingWeek(listOf(typicalMonday, typicalMonday.copy(day = WEDNESDAY.input)))
    }

    @Test
    fun `fail when weekday is empty`() {
        val message = assertThrows<RuntimeException> {
            WeekValidator.validateWorkingWeek(emptyList())
        }.message

        assertEquals(EMPTY_WEEK, message)
    }

    @Test
    fun `fail when weekday is repeated`() {
        val message = assertThrows<RuntimeException> {
            WeekValidator.validateWorkingWeek(listOf(typicalMonday, typicalMonday))
        }.message

        assertEquals(DAYS_ARE_REPEATED, message)
    }

    @Test
    fun `fail when weekday has a wrong format`() {
        assertThrows<RuntimeException> {
            WeekValidator.validateWorkingWeek(listOf(
                typicalMonday.copy(day = "wrong day")
            ))
        }
    }

    @Test
    fun `fail when inconsistent open and closing times`() {
        val message = assertThrows<RuntimeException> {
            WeekValidator.validateWorkingWeek(listOf(typicalMonday.copy(
                transitions = listOf(
                    Transition(CLOSE.input, ONE_PM_UNIX),
                    Transition(OPEN.input, SIX_PM_UNIX),
                )
            )))
        }.message

        assertEquals(INCONSISTENT_ORDER_OPEN_CLOSE_TIMES, message)
    }

    @Test
    fun `unfinished opening times cannot be processed`() {
        val message = assertThrows<RuntimeException> {
            WeekValidator.validateWorkingWeek(listOf(typicalMonday.copy(transitions = listOf(nineToEleven.first()))))
        }.message

        assertEquals(TIMES_ARE_INCONSISTENT, message)
    }
}

