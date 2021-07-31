package com.almeydajuan.openinghours.validator

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
        if (workingDays.isEmpty()) {
            throw RuntimeException(EMPTY_WEEK)
        }
        return true
    }
}

const val EMPTY_WEEK = "Week is empty"
const val DAYS_ARE_REPEATED = "Days are repeated"