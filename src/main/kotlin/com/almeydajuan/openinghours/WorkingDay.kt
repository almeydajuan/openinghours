package com.almeydajuan.openinghours

data class WorkingDay(val day: String, val actions: List<DayAction>) {
    fun isEmptyWorkingDay() = actions.size < 2
    fun isComplete() = actions.isNotEmpty() && actions.last().action == Action.CLOSE.input
}