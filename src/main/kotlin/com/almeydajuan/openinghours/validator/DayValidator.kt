package com.almeydajuan.openinghours.validator

import com.almeydajuan.openinghours.ACTION_NOT_SUPPORTED
import com.almeydajuan.openinghours.ActionParser
import com.almeydajuan.openinghours.DAY_NOT_SUPPORTED
import com.almeydajuan.openinghours.DayAction
import com.almeydajuan.openinghours.DayParser
import com.almeydajuan.openinghours.TIMES_ARE_INCONSISTENT

data class DayValidator(
    val dayParser: DayParser,
    val actionParser: ActionParser
    ) {

    fun isValid(day: String, actions: List<DayAction>): Boolean {
        if (!dayParser.containsDay(day)) {
            throw RuntimeException(DAY_NOT_SUPPORTED)
        }
        if (actions.any { !actionParser.containsAction(it.action) }) {
            throw RuntimeException(ACTION_NOT_SUPPORTED)
        }
        if (!actions.isSorted()) {
            throw RuntimeException(TIMES_ARE_INCONSISTENT)
        }
        return true
    }

    private fun List<DayAction>.isSorted(): Boolean =
        this.map { it.timestamp }.sorted() == this.map { it.timestamp }
}