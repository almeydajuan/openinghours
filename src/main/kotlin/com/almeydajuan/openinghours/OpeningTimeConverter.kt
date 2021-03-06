package com.almeydajuan.openinghours

import com.almeydajuan.openinghours.provider.DayProvider

class OpeningTimeConverter {

    companion object {
        fun convert(workingWeek: WorkingWeek): List<OpeningTime> {
            val workingHoursWithOffset = workingWeek.flatMap { workingDay ->
                workingDay.transitions.map {
                    Transition(
                        action = it.action,
                        timestamp = DayProvider.calculateTimeForDay(
                            DayProvider.findDayByInputName(workingDay.day),
                            it.timestamp
                        )
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