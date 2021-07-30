package com.almeydajuan.openinghours

data class WorkingWeekParser(
    val workingDayParser: WorkingDayParser
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
            text.append(workingDayParser.parseCloseWorkingDay(workingDay))
        } else {
            text.append(workingDayParser.parseNormalWorkingDay(workingDay))
        }
        return text.toString()
    }
}