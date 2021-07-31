package com.almeydajuan.openinghours

import com.almeydajuan.openinghours.provider.Action.CLOSE
import com.almeydajuan.openinghours.provider.Action.OPEN
import com.almeydajuan.openinghours.provider.DAY_OFFSET
import com.almeydajuan.openinghours.provider.Day
import com.almeydajuan.openinghours.provider.Day.MONDAY
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OpeningTimeConverterTest {

    @Test
    fun `Mondays open from 9 am to 11 am can be processed`() {
        assertEquals(listOf(openingNineToEleven), OpeningTimeConverter.convert(listOf(typicalMonday)))
    }

    @Test
    fun `Mondays open from 9 am to 11 am and from 1 pm to 6 pm can be processed`() {
        assertEquals(
            listOf(openingNineToEleven, openingOneToSix),
            OpeningTimeConverter.convert(
                listOf(
                    WorkingDay(
                        day = MONDAY.input,
                        transitions = nineToEleven + oneToSix
                    )
                )
            )
        )
    }

    @Test
    fun `several days can be processed`() {
        assertEquals(
            listOf(
                openingNineToEleven,
                openingNineToEleven.copy(openingTime = NINE_AM_UNIX + DAY_OFFSET, closingTime = ELEVEN_AM_UNIX + DAY_OFFSET)
            ),
            OpeningTimeConverter.convert(
                listOf(
                    typicalMonday,
                    typicalMonday.copy(day = Day.TUESDAY.input)
                )
            )
        )
    }

    /**
     * Monday: 1 PM - 1 AM
     */
    @Test
    fun `split times can be processed`() {
        assertEquals(
            listOf(
                OpeningTime(ONE_PM_UNIX, ONE_AM_UNIX + DAY_OFFSET)
            ),
            OpeningTimeConverter.convert(
                listOf(
                    WorkingDay(
                        day = MONDAY.input,
                        transitions = listOf(Transition(OPEN.input, ONE_PM_UNIX))
                    ),
                    WorkingDay(
                        day = Day.TUESDAY.input,
                        transitions = listOf(Transition(CLOSE.input, ONE_AM_UNIX))
                    )
                )
            )
        )
    }
}