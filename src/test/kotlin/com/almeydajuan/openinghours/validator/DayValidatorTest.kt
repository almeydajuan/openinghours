package com.almeydajuan.openinghours.validator

import com.almeydajuan.openinghours.ACTION_NOT_SUPPORTED
import com.almeydajuan.openinghours.Action
import com.almeydajuan.openinghours.DAY_NOT_SUPPORTED
import com.almeydajuan.openinghours.Day
import com.almeydajuan.openinghours.ELEVEN_AM_UNIX
import com.almeydajuan.openinghours.NINE_AM_UNIX
import com.almeydajuan.openinghours.Transition
import com.almeydajuan.openinghours.nineToEleven
import com.almeydajuan.openinghours.oneToSix
import com.almeydajuan.openinghours.typicalMonday
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DayValidatorTest {

    @Test
    fun `fail when day is not accepted`() {
        val message = assertThrows<RuntimeException> {
            DayValidator.isValid(
                typicalMonday.copy(
                    day = "someday"
                )
            )
        }.message

        assertEquals(DAY_NOT_SUPPORTED, message)
    }

    @Test
    fun `fail when transition is not accepted`() {
        val message = assertThrows<RuntimeException> {
            DayValidator.isValid(
                typicalMonday.copy(
                    transitions = listOf(
                        Transition("someaction", NINE_AM_UNIX),
                        Transition(Action.OPEN.input, ELEVEN_AM_UNIX)
                    )
                )
            )
        }.message

        assertEquals(ACTION_NOT_SUPPORTED, message)
    }

    @Test
    fun `fail when times are not ordered`() {
        val message = assertThrows<RuntimeException> {
            DayValidator.isValid(
                typicalMonday.copy(
                    transitions = nineToEleven.reversed()
                )
            )
        }.message

        assertEquals(TIMES_ARE_INCONSISTENT, message)
    }

    @Test
    fun `fail when underflow value`() {
        val message = assertThrows<RuntimeException> {
            DayValidator.isValid(
                typicalMonday.copy(
                    transitions = nineToEleven.map { it.copy(timestamp = -1) }
                )
            )
        }.message

        assertEquals(OUT_OF_RANGE_DATE, message)
    }

    @Test
    fun `fail when overflow value`() {
        val message = assertThrows<RuntimeException> {
            DayValidator.isValid(
                typicalMonday.copy(
                    transitions = nineToEleven.map { it.copy(timestamp = 86400) }
                )
            )
        }.message

        assertEquals(OUT_OF_RANGE_DATE, message)
    }

    @Test
    fun `Mondays open from 9 am to 11 am is valid`() {
        assertTrue(DayValidator.isValid(typicalMonday))
    }

    @Test
    fun `Mondays open from 9 am to 11 am and from 1 pm to 6 pm is valid`() {
        assertTrue(DayValidator.isValid(typicalMonday.copy(transitions = nineToEleven + oneToSix)))
    }

    /**
     *  This is validated:
     *
     *  Monday: 9 AM - 11 AM, 1 PM - 6 PM
     *  Wednesday: 9 AM - 11 AM, 1 PM - 6 PM
     */
    @Test
    fun `Several days with several times are valid`() {
        assertTrue(DayValidator.isValid(typicalMonday.copy(transitions = nineToEleven + oneToSix)))
        assertTrue(DayValidator.isValid(typicalMonday.copy(day = Day.WEDNESDAY.input, transitions = nineToEleven + oneToSix)))
    }

    @Test
    fun `Empty day is valid`() {
        assertTrue(DayValidator.isValid(typicalMonday.copy(transitions = emptyList())))
    }

    @Test
    fun `Day with only closing time is valid`() {
        assertTrue(DayValidator.isValid(typicalMonday.copy(transitions = listOf(Transition(Action.CLOSE.input, ELEVEN_AM_UNIX)))))
    }
}