package com.almeydajuan.openinghours

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TestXX {

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
    }

    @Test
    fun `Several days can be processed`() {
    }

    @Test
    fun `split times can be processed`() {
    }
}