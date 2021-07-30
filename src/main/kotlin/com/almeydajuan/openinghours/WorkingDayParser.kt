package com.almeydajuan.openinghours

import java.lang.RuntimeException
import java.lang.StringBuilder

data class WorkingDayParser(
    val timeConverter: UnixTimestampConverter,
    val dayParser: DayParser,
    val actionParser: ActionParser
) {

    fun convert(day: String, actions: List<DayAction>): String {
        validateInput(day, actions)

        val text = StringBuilder(dayParser.parseDay(day))
        text.append(" ")
        text.append(timeConverter.convert(actions[0].timestamp))
        text.append(" ")
        text.append(actionParser.parseAction(actions[1].action))
        text.append(" ")
        text.append(timeConverter.convert(actions[1].timestamp))

        return text.toString()
    }

    private fun validateInput(day: String, actions: List<DayAction>) {
        if (!dayParser.containsDay(day)) {
            throw RuntimeException(DAY_NOT_SUPPORTED)
        }
        if (actions.any { !actionParser.containsAction(it.action) }) {
            throw RuntimeException(ACTION_NOT_SUPPORTED)
        }
    }
}

data class DayAction(val action: String, val timestamp: Long)

const val DAY_NOT_SUPPORTED = "Day is not supported"
const val ACTION_NOT_SUPPORTED = "Action is not supported"