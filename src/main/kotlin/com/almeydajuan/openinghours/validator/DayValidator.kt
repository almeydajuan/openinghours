package com.almeydajuan.openinghours.validator

import com.almeydajuan.openinghours.WorkingDay
import com.almeydajuan.openinghours.areSorted
import com.almeydajuan.openinghours.provider.ActionProvider
import com.almeydajuan.openinghours.provider.DayProvider

class DayValidator {

    companion object {
        fun isValid(workingDay: WorkingDay): Boolean {
            if (!DayProvider.containsDay(workingDay.day)) {
                throw ValidationException(DAY_NOT_SUPPORTED)
            }
            if (workingDay.transitions.any { !ActionProvider.containsAction(it.action) }) {
                throw ValidationException(ACTION_NOT_SUPPORTED)
            }
            if (!workingDay.transitions.areSorted()) {
                throw ValidationException(TIMES_ARE_INCONSISTENT)
            }
            if (workingDay.transitions.any { isOutOfRange(it.timestamp) }) {
                throw ValidationException(OUT_OF_RANGE_DATE)
            }
            return true
        }

        private fun isOutOfRange(unixTimestamp: Long) = unixTimestamp < 0 || unixTimestamp > 86399
    }
}

const val OUT_OF_RANGE_DATE = "date is out of range"
const val DAY_NOT_SUPPORTED = "Day is not supported"
const val ACTION_NOT_SUPPORTED = "Action is not supported"
