package com.almeydajuan.openinghours.service

import com.almeydajuan.openinghours.OpeningTimeConverter
import com.almeydajuan.openinghours.WorkingWeek
import com.almeydajuan.openinghours.parser.WeekParser
import com.almeydajuan.openinghours.validator.WeekValidator

data class ParsingService(
    private val weekParser: WeekParser,
    private val weekScheduleRepository: WeekScheduleRepository
) {
    fun parseOpeningHours(workingWeek: WorkingWeek): String {
        WeekValidator.validateWorkingWeek(workingWeek)
        val weekSchedule = OpeningTimeConverter.convert(workingWeek)
        weekScheduleRepository.saveWeekSchedule(weekSchedule)
        return weekParser.parseWeek(weekSchedule)
    }
}