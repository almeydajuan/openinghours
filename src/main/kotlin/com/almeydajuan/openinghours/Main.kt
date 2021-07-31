package com.almeydajuan.openinghours

import com.almeydajuan.openinghours.parser.DayParser
import com.almeydajuan.openinghours.parser.UnixTimestampParser
import com.almeydajuan.openinghours.parser.WeekParser
import com.almeydajuan.openinghours.provider.Action
import com.almeydajuan.openinghours.provider.Day

fun main() {
    val parsingService = ParsingService(WeekParser(DayParser(UnixTimestampParser())))
    println(
        parsingService.parseOpeningHours(
            listOf(
                WorkingDay(
                    day = Day.MONDAY.input,
                    transitions = listOf(
                        Transition(Action.OPEN.input, 32400),
                        Transition(Action.CLOSE.input, 39600)
                    )
                )
            )
        )
    )
}