package com.almeydajuan.openinghours

class DayParser {
    fun containsDay(dayName: String) = Day.values().map { it.input }.contains(dayName)
    fun parseDay(dayName: String) = Day.values().first { it.input == dayName }.output
}

private enum class Day(val input: String, val output: String) {
    MONDAY("monday", "Monday"),
    TUESDAY("tuesday", "Tuesday"),
    WEDNESDAY("wednesday", "Wednesday"),
    THURSDAY("thursday", "Thursday"),
    FRIDAY("friday", "Friday"),
    SATURDAY("saturday", "Saturday"),
    SUNDAY("sunday", "Sunday")
}