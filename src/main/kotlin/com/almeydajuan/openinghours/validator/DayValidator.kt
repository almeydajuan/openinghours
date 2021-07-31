package com.almeydajuan.openinghours.validator

import com.almeydajuan.openinghours.ACTION_NOT_SUPPORTED
import com.almeydajuan.openinghours.ActionProvider
import com.almeydajuan.openinghours.DAY_NOT_SUPPORTED
import com.almeydajuan.openinghours.DayProvider
import com.almeydajuan.openinghours.WorkingDay
import com.almeydajuan.openinghours.areSorted

class DayValidator {

    companion object {
        fun isValid(workingDay: WorkingDay): Boolean {
            if (!DayProvider.containsDay(workingDay.day)) {
                throw RuntimeException(DAY_NOT_SUPPORTED)
            }
            if (workingDay.transitions.any { !ActionProvider.containsAction(it.action) }) {
                throw RuntimeException(ACTION_NOT_SUPPORTED)
            }
            if (!workingDay.transitions.areSorted()) {
                throw RuntimeException(TIMES_ARE_INCONSISTENT)
            }
            if (workingDay.transitions.any { isOutOfRange(it.timestamp) }) {
                throw RuntimeException(OUT_OF_RANGE_DATE)
            }
            return true
        }

        private fun isOutOfRange(unixTimestamp: Long) = unixTimestamp < 0 || unixTimestamp > 86399
    }
}

const val OUT_OF_RANGE_DATE = "date is out of range"
