package com.almeydajuan.openinghours

import com.almeydajuan.openinghours.provider.Action

data class WorkingDay(val day: String, val transitions: RestaurantTransitions) {
    fun isEmptyWorkingDay() = transitions.size < 2
    fun isComplete() = transitions.isNotEmpty() && transitions.last().action == Action.CLOSE.input
}