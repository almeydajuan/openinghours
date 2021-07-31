package com.almeydajuan.openinghours

class DayProvider {
    companion object {
        fun findDayByInputName(dayName: String) = Day.values().first { it.input == dayName }

        fun containsDay(dayName: String) = Day.values().map { it.input }.contains(dayName)

        fun parseDay(dayName: String) = findDayByInputName(dayName).output

        fun calculateTimeForDay(day: Day, timestamp: Long) = timestamp + day.ordinal * DAY_OFFSET

        fun moveOpeningToDay(day: Day, openingTime: OpeningTime) = openingTime.copy(
            openingTime = calculateTimeForDay(day, openingTime.openingTime),
            closingTime = calculateTimeForDay(day, openingTime.closingTime)
        )

        fun calculateDayOfTheWeek(unixTime: Long) = Day.values()[(unixTime / DAY_OFFSET).toInt()]
    }
}

enum class Day(val input: String, val output: String) {
    MONDAY("monday", "Monday"),
    TUESDAY("tuesday", "Tuesday"),
    WEDNESDAY("wednesday", "Wednesday"),
    THURSDAY("thursday", "Thursday"),
    FRIDAY("friday", "Friday"),
    SATURDAY("saturday", "Saturday"),
    SUNDAY("sunday", "Sunday")
}

const val DAY_OFFSET = 86400L