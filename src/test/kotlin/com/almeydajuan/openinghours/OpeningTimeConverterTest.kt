package com.almeydajuan.openinghours

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class OpeningTimeConverterTest {

    @Test
    fun `unfinished opening times cannot be processed`() {
        val message = assertThrows<RuntimeException> {
            OpeningTimeConverter.convert(listOf(typicalMonday.copy(actions = listOf(nineToEleven.first()))))
        }.message

        assertEquals(TIMES_ARE_INCONSISTENT, message)
    }

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
                        day = Day.MONDAY.input,
                        actions = nineToEleven + oneToSix
                    )
                )
            )
        )
    }

    @Test
    fun `Several days can be processed`() {
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
                        day = Day.MONDAY.input,
                        actions = listOf(DayAction(Action.OPEN.input, ONE_PM_UNIX))
                    ),
                    WorkingDay(
                        day = Day.TUESDAY.input,
                        actions = listOf(DayAction(Action.CLOSE.input, ONE_AM_UNIX))
                    )
                )
            )
        )
    }
}