package com.almeydajuan.openinghours

import java.lang.RuntimeException

data class WorkingDayParser(
    val timeConverter: UnixTimestampConverter,
    val dayParser: DayParser,
    val actionParser: ActionParser
) {

    fun convert(day: String, action1: String, time1: Long, action2: String, time2: Long): String {
        validateInput(day, action1, action2)

        val dayText = dayParser.parseDay(day)
        val openText = actionParser.parseAction(action1)
        val openTime = timeConverter.convert(time1)
        val closeText = actionParser.parseAction(action2)
        val closeTime = timeConverter.convert(time2)

        return "$dayText $openText $openTime $closeText $closeTime"
    }

    private fun validateInput(day: String, action1: String, action2: String) {
        if (!dayParser.containsDay(day)) {
            throw RuntimeException(DAY_NOT_SUPPORTED)
        }
        if (!actionParser.containsAction(action1)) {
            throw RuntimeException(ACTION_NOT_SUPPORTED)
        }
        if (!actionParser.containsAction(action2)) {
            throw RuntimeException(ACTION_NOT_SUPPORTED)
        }
    }
}

const val DAY_NOT_SUPPORTED = "Day is not supported"
const val ACTION_NOT_SUPPORTED = "Action is not supported"