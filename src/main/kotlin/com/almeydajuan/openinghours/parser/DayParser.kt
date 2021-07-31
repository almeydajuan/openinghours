package com.almeydajuan.openinghours.parser

import com.almeydajuan.openinghours.Day
import com.almeydajuan.openinghours.OpeningTime
import com.almeydajuan.openinghours.UnixTimestampConverter

data class DayParser(private val timeConverter: UnixTimestampConverter) {

    fun parseWorkingDay(day: Day, openingTimes: List<OpeningTime>): String {
        val text = StringBuffer()
        text.append(day.output)
        text.append(": ")
        if (openingTimes.isEmpty()) {
            text.append("Closed")
        } else {
            openingTimes.forEachIndexed { index, openingTime ->
                if (index > 0) {
                    text.append(", ")
                }
                text.append(timeConverter.convert(openingTime.openingTime))
                text.append(" - ")
                text.append(timeConverter.convert(openingTime.closingTime))
            }
        }
        return text.toString()
    }
}