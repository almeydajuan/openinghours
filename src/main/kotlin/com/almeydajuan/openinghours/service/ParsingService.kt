package com.almeydajuan.openinghours.service

import com.almeydajuan.openinghours.OpeningTimeConverter
import com.almeydajuan.openinghours.WorkingWeek
import com.almeydajuan.openinghours.parser.WeekParser
import com.almeydajuan.openinghours.validator.WeekValidator

data class ParsingService(
    private val weekParser: WeekParser
)
{
    fun parseOpeningHours(workingWeek: WorkingWeek): String {
        WeekValidator.validateWorkingWeek(workingWeek)
        return weekParser.parseWeek(OpeningTimeConverter.convert(workingWeek))
    }
}