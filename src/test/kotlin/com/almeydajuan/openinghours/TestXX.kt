package com.almeydajuan.openinghours

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TestXX {

    @Test
    fun `unfinished opening times cannot be processed`() {
        val message = assertThrows<RuntimeException> {
            convert(listOf(typicalMonday.copy(actions = listOf(nineToEleven.first()))))
        }.message

        assertEquals(TIMES_ARE_INCONSISTENT, message)
    }

    private fun convert(workingDays: List<WorkingDay>): List<OpeningTime> {
        val weeklyActions = workingDays.flatMap { it.actions }
        if (weeklyActions.count { it.action == Action.OPEN.input }
            != weeklyActions.count { it.action == Action.CLOSE.input }) {
            throw RuntimeException(TIMES_ARE_INCONSISTENT)
        }


        return workingDays
            .map { workingDay ->
                WorkingDay(
                    workingDay.day,
                    workingDay.actions.map {
                        DayAction(
                            action = it.action,
                            timestamp = it.timestamp + DayProvider.calculateOffset(workingDay.day)
                        )
                    }
                )
            }
            .flatMap { it.actions }
            .zipWithNext()
            .filter {
                it.first.action == Action.OPEN.input
            }
            .map {
                OpeningTime(
                    it.first.timestamp,
                    it.second.timestamp
                )
            }
    }

    @Test
    fun `Mondays open from 9 am to 11 am can be processed`() {
        assertEquals(listOf(openingNineToEleven), convert(listOf(typicalMonday)))
    }

    @Test
    fun `Mondays open from 9 am to 11 am and from 1 pm to 6 pm can be processed`() {
    }

    @Test
    fun `Several days can be processed`() {
    }

    @Test
    fun `split times can be processed`() {
    }
}

data class OpeningTime(
    private val openingTime: Long,
    private val closingTime: Long
)