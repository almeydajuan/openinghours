package com.almeydajuan.openinghours

class DayProvider {
    companion object {
        fun containsDay(dayName: String) = Day.values().map { it.input }.contains(dayName)

        fun findDayByInputName(dayName: String) = Day.values().first { it.input == dayName }

        fun parseDay(dayName: String) = findDayByInputName(dayName).output

        fun calculateOffset(dayName: String): Int {
            return findDayByInputName(dayName).ordinal * DAY_OFFSET
        }
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

const val DAY_OFFSET = 86400