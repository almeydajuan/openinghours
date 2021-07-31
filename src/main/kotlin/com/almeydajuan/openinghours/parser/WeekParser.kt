package com.almeydajuan.openinghours.parser

import com.almeydajuan.openinghours.OpeningTime
import com.almeydajuan.openinghours.provider.Day
import com.almeydajuan.openinghours.provider.DayProvider

data class WeekParser(val dayParser: DayParser) {

    fun parseWeek(weekSchedule: WeekSchedule): String {
        val text = StringBuffer()
        Day.values().forEach { day ->
            if (text.isNotBlank()) {
                text.append("\n")
            }
            val scheduleForTheDay = weekSchedule.filter { DayProvider.calculateDayOfTheWeek(it.openingTime) == day }
            text.append(dayParser.parseWorkingDay(day, scheduleForTheDay))
        }
        return text.toString()
    }
}

typealias WeekSchedule = List<OpeningTime>