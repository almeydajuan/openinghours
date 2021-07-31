package com.almeydajuan.openinghours

class OpeningTimeConverter {

    companion object {
        fun convert(workingWeek: WorkingWeek): List<OpeningTime> {
            val workingHoursWithOffset = workingWeek.flatMap { workingDay ->
                workingDay.transitions.map {
                    Transition(
                        action = it.action,
                        timestamp = it.timestamp + DayProvider.calculateOffset(workingDay.day)
                    )
                }
            }

            return workingHoursWithOffset
                .zipWithNext()
                .filter { it.first.isOpen() && it.second.isClose() }
                .map {
                    OpeningTime(
                        it.first.timestamp,
                        it.second.timestamp
                    )
                }
        }
    }
}

data class OpeningTime(
    val openingTime: Long,
    val closingTime: Long
)