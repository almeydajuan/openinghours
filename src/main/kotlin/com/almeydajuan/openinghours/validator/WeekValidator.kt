package com.almeydajuan.openinghours.validator

import com.almeydajuan.openinghours.Day
import com.almeydajuan.openinghours.WorkingDay

data class WeekValidator(
    private val dayValidator: DayValidator
) {

    fun isValid(workingDays: List<WorkingDay>): Boolean {
        workingDays.forEach {
            dayValidator.isValid(it.day, it.actions)
        }
        if (workingDays.map { it.day }.toSet().size != workingDays.size) {
            throw RuntimeException(DAYS_ARE_REPEATED)
        }
        if (workingDays.map { it.day }.size != Day.values().size) {
            throw RuntimeException(DAYS_MISSING_IN_THE_WEEK)
        }

        return true
    }
}

const val DAYS_MISSING_IN_THE_WEEK = "Days are missing"
const val DAYS_ARE_REPEATED = "Days are repeated"