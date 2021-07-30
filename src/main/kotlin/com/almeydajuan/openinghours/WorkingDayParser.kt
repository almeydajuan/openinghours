package com.almeydajuan.openinghours

data class WorkingDayParser(
    val timeConverter: UnixTimestampConverter,
    val dayParser: DayParser,
    val actionParser: ActionParser
) {

    fun convertWorkingDays(workingDays: List<WorkingDay>): String {
        val text = StringBuilder("")
        workingDays.forEach {
            if (text.isNotBlank()) {
                text.append("\n")
            }
            text.append(convertWorkingDay(it))
        }
        return text.toString()
    }

    private fun convertWorkingDay(workingDay: WorkingDay): String {
        val text = StringBuilder()
        if (workingDay.isEmptyWorkingDay()) {
            text.append(dayParser.parseDay(workingDay.day))
            text.append(": Closed")
        } else {
            validateInput(workingDay.day, workingDay.actions)

            text.append(dayParser.parseDay(workingDay.day))
            text.append(": ")

            workingDay.actions.zipWithNext().forEach {
                val first = it.first
                val second = it.second
                if (first.action == Action.OPEN.input && it.second.action == Action.CLOSE.input) {
                    text.append(timeConverter.convert(first.timestamp))
                    text.append(" ")
                    text.append(actionParser.parseAction(second.action))
                    text.append(" ")
                    text.append(timeConverter.convert(second.timestamp))
                }
                if (first.action == Action.CLOSE.input) {
                    text.append(", ")
                }
            }
        }
        return text.toString()
    }

    private fun validateInput(day: String, actions: List<DayAction>) {
        if (!dayParser.containsDay(day)) {
            throw RuntimeException(DAY_NOT_SUPPORTED)
        }
        if (actions.any { !actionParser.containsAction(it.action) }) {
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

data class WorkingDay(val day: String, val actions: List<DayAction>) {
    fun isEmptyWorkingDay() = actions.size < 2
}

const val DAY_NOT_SUPPORTED = "Day is not supported"
const val ACTION_NOT_SUPPORTED = "Action is not supported"
const val TIMES_ARE_INCONSISTENT = "Times are inconsistent"