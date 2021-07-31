package com.almeydajuan.openinghours.validator

import com.almeydajuan.openinghours.ACTION_NOT_SUPPORTED
import com.almeydajuan.openinghours.Action
import com.almeydajuan.openinghours.ActionParser
import com.almeydajuan.openinghours.DAY_NOT_SUPPORTED
import com.almeydajuan.openinghours.Day
import com.almeydajuan.openinghours.DayAction
import com.almeydajuan.openinghours.DayParser
import com.almeydajuan.openinghours.ELEVEN_AM_UNIX
import com.almeydajuan.openinghours.NINE_AM_UNIX
import com.almeydajuan.openinghours.TIMES_ARE_INCONSISTENT
import com.almeydajuan.openinghours.nineToEleven
import com.almeydajuan.openinghours.oneToSix
import com.almeydajuan.openinghours.typicalMonday
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DayValidatorTest {

    private val dayValidator = DayValidator(DayParser(), ActionParser())

    @Test
    fun `fail when day is not accepted`() {
        val message = assertThrows<RuntimeException> {
            dayValidator.isValid(
                day = "someday",
                actions = nineToEleven
            )
        }.message

        assertEquals(DAY_NOT_SUPPORTED, message)
    }

    @Test
    fun `fail when action is not accepted`() {
        val message = assertThrows<RuntimeException> {
            dayValidator.isValid(
                day = Day.MONDAY.input,
                actions = listOf(
                    DayAction("someaction", NINE_AM_UNIX),
                    DayAction(Action.OPEN.input, ELEVEN_AM_UNIX)
                )
            )
        }.message

        assertEquals(ACTION_NOT_SUPPORTED, message)
    }

    @Test
    fun `fail when times are not ordered`() {
        val message = assertThrows<RuntimeException> {
            dayValidator.isValid(
                day = Day.MONDAY.input,
                actions = nineToEleven.reversed()
            )
        }.message

        assertEquals(TIMES_ARE_INCONSISTENT, message)
    }

    @Test
    fun `Mondays open from 9 am to 11 am is valid`() {
        assertTrue(dayValidator.isValid(typicalMonday.day, typicalMonday.actions))
    }

    @Test
    fun `Mondays open from 9 am to 11 am and from 1 pm to 6 pm is valid`() {
        assertTrue(dayValidator.isValid(day = Day.MONDAY.input, actions = nineToEleven + oneToSix))
    }

    /**
     *  This is validated:
     *
     *  Monday: 9 AM - 11 AM, 1 PM - 6 PM
     *  Wednesday: 9 AM - 11 AM, 1 PM - 6 PM
     */
    @Test
    fun `Several days with several times are valid`() {
        assertTrue(dayValidator.isValid(day = Day.MONDAY.input, actions = nineToEleven + oneToSix))
        assertTrue(dayValidator.isValid(day = Day.WEDNESDAY.input, actions = nineToEleven + oneToSix))
    }

    @Test
    fun `Empty day is valid`() {
        assertTrue(dayValidator.isValid(day = Day.MONDAY.input, actions = emptyList()))
    }

    @Test
    fun `Day with only closing time is valid`() {
        assertTrue(dayValidator.isValid(day = Day.MONDAY.input, actions = listOf(DayAction(Action.CLOSE.input, ELEVEN_AM_UNIX))))
    }
}