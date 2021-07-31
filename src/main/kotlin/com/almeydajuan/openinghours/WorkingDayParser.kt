package com.almeydajuan.openinghours

class WorkingDayParser {

    fun parseCloseWorkingDay(workingDay: WorkingDay) =
        "${DayProvider.parseDay(workingDay.day)}: Closed"

    fun parseNormalWorkingDay(workingDay: WorkingDay): String {
        val text = StringBuffer()
        validateInput(workingDay.day, workingDay.actions)

        text.append(DayProvider.parseDay(workingDay.day))
        text.append(": ")

        workingDay.actions.zipWithNext().forEach {
            val first = it.first
            val second = it.second
            if (first.action == Action.OPEN.input && it.second.action == Action.CLOSE.input) {
                text.append(UnixTimestampConverter.convert(first.timestamp))
                text.append(" ")
                text.append(ActionProvider.parseAction(second.action))
                text.append(" ")
                text.append(UnixTimestampConverter.convert(second.timestamp))
            }
            if (first.action == Action.CLOSE.input) {
                text.append(", ")
            }
        }
        return text.toString()
    }

    private fun validateInput(day: String, actions: List<DayAction>) {
        if (!DayProvider.containsDay(day)) {
            throw RuntimeException(DAY_NOT_SUPPORTED)
        }
        if (actions.any { !ActionProvider.containsAction(it.action) }) {
            throw RuntimeException(ACTION_NOT_SUPPORTED)
        }
        if (!actions.isSorted()) {
            throw RuntimeException(TIMES_ARE_INCONSISTENT)
        }
    }
}

private fun List<DayAction>.isSorted(): Boolean =
    this.map { it.timestamp }.sorted() == this.map { it.timestamp }

data class DayAction(val action: String, val timestamp: Long)

const val DAY_NOT_SUPPORTED = "Day is not supported"
const val ACTION_NOT_SUPPORTED = "Action is not supported"
const val TIMES_ARE_INCONSISTENT = "Times are inconsistent"