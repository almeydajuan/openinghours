package com.almeydajuan.openinghours

data class WorkingDay(val day: String, val transitions: RestaurantTransitions) {
    fun isEmptyWorkingDay() = transitions.size < 2
    fun isComplete() = transitions.isNotEmpty() && transitions.last().action == Action.CLOSE.input
}