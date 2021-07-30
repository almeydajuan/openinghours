package com.almeydajuan.openinghours

import java.lang.RuntimeException

data class WorkingDayParser(
    val timeConverter: UnixTimestampConverter,
    val dayParser: DayParser,
    val actionParser: ActionParser
) {

    fun convert(day: String, actions: List<DayAction>): String {
        validateInput(day, actions)

        val dayText = dayParser.parseDay(day)
        val openTime = timeConverter.convert(actions[0].timestamp)
        val closeText = actionParser.parseAction(actions[1].action)
        val closeTime = timeConverter.convert(actions[1].timestamp)

        return "$dayText $openTime $closeText $closeTime"
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