package com.almeydajuan.openinghours

import com.almeydajuan.openinghours.validator.TIMES_ARE_INCONSISTENT

@Deprecated("to remove soon")
class WorkingDayParser {

    private val timeConverter = UnixTimestampConverter()

    fun parseCloseWorkingDay(workingDay: WorkingDay) =
        "${DayProvider.parseDay(workingDay.day)}: Closed"

    fun parseNormalWorkingDay(workingDay: WorkingDay): String {
        val text = StringBuffer()
        validateInput(workingDay.day, workingDay.transitions)

        text.append(DayProvider.parseDay(workingDay.day))
        text.append(": ")

        workingDay.transitions.zipWithNext().forEach {
            val first = it.first
            val second = it.second
            if (first.action == Action.OPEN.input && it.second.action == Action.CLOSE.input) {
                text.append(timeConverter.convert(first.timestamp))
                text.append(" ")
                text.append(ActionProvider.parseAction(second.action))
                text.append(" ")
                text.append(timeConverter.convert(second.timestamp))
            }
            if (first.action == Action.CLOSE.input) {
                text.append(", ")
            }
        }
        return text.toString()
    }

    private fun validateInput(day: String, actions: List<Transition>) {
        if (!DayProvider.containsDay(day)) {
            throw RuntimeException(DAY_NOT_SUPPORTED)
        }
        if (actions.any { !ActionProvider.containsAction(it.action) }) {
            throw RuntimeException(ACTION_NOT_SUPPORTED)
        }
        if (!actions.areSorted()) {
            throw RuntimeException(TIMES_ARE_INCONSISTENT)
        }
    }
}

data class Transition(val action: String, val timestamp: Long) {
    fun isOpen() = action == Action.OPEN.input
    fun isClose() = action == Action.CLOSE.input
}

const val DAY_NOT_SUPPORTED = "Day is not supported"
const val ACTION_NOT_SUPPORTED = "Action is not supported"