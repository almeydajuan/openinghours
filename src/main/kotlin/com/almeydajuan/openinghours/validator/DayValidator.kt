package com.almeydajuan.openinghours.validator

import com.almeydajuan.openinghours.ACTION_NOT_SUPPORTED
import com.almeydajuan.openinghours.ActionProvider
import com.almeydajuan.openinghours.DAY_NOT_SUPPORTED
import com.almeydajuan.openinghours.DayAction
import com.almeydajuan.openinghours.DayProvider
import com.almeydajuan.openinghours.TIMES_ARE_INCONSISTENT

class DayValidator {

    companion object {
        fun isValid(day: String, actions: List<DayAction>): Boolean {
            if (!DayProvider.containsDay(day)) {
                throw RuntimeException(DAY_NOT_SUPPORTED)
            }
            if (actions.any { !ActionProvider.containsAction(it.action) }) {
                throw RuntimeException(ACTION_NOT_SUPPORTED)
            }
            if (!actions.isSorted()) {
                throw RuntimeException(TIMES_ARE_INCONSISTENT)
            }
            if (actions.any { isOutOfRange(it.timestamp) }) {
                throw RuntimeException(OUT_OF_RANGE_DATE)
            }
            return true
        }

        private fun isOutOfRange(unixTimestamp: Long) = unixTimestamp < 0 || unixTimestamp > 86399
    }
}

private fun List<DayAction>.isSorted(): Boolean =
    this.map { it.timestamp }.sorted() == this.map { it.timestamp }

const val OUT_OF_RANGE_DATE = "date is out of range"
