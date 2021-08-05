package com.almeydajuan.openinghours.validator

import com.almeydajuan.openinghours.WorkingWeek
import com.almeydajuan.openinghours.areDaysRepeated
import com.almeydajuan.openinghours.areOpeningAndClosingTimesInConsistentOrder
import com.almeydajuan.openinghours.isAmountOfOpeningAndClosingActionsConsistent

class WeekValidator {

    companion object {
        fun validateWorkingWeek(workingWeek: WorkingWeek) {
            workingWeek.forEach { DayValidator.isValid(it) }
            if (workingWeek.areDaysRepeated()) {
                throw ValidationException(DAYS_ARE_REPEATED)
            }
            if (workingWeek.isEmpty()) {
                throw ValidationException(EMPTY_WEEK)
            }
            val restaurantTransitions = workingWeek.flatMap { it.transitions }
            if (restaurantTransitions.isAmountOfOpeningAndClosingActionsConsistent()) {
                throw ValidationException(TIMES_ARE_INCONSISTENT)
            }
            if (restaurantTransitions.areOpeningAndClosingTimesInConsistentOrder()) {
                throw ValidationException(INCONSISTENT_ORDER_OPEN_CLOSE_TIMES)
            }
        }
    }
}

const val EMPTY_WEEK = "Week is empty"
const val DAYS_ARE_REPEATED = "Days are repeated"
const val TIMES_ARE_INCONSISTENT = "Times are inconsistent"
const val INCONSISTENT_ORDER_OPEN_CLOSE_TIMES = "The order between open and close time is inconsistent"