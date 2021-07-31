package com.almeydajuan.openinghours

class OpeningTimeConverter {

    companion object {
        fun convert(workingDays: List<WorkingDay>): List<OpeningTime> {
            val weeklyActions = workingDays.flatMap { it.actions }
            if (weeklyActions.count { it.action == Action.OPEN.input }
                != weeklyActions.count { it.action == Action.CLOSE.input }) {
                throw RuntimeException(TIMES_ARE_INCONSISTENT)
            }


            return workingDays
                .map { workingDay ->
                    WorkingDay(
                        workingDay.day,
                        workingDay.actions.map {
                            DayAction(
                                action = it.action,
                                timestamp = it.timestamp + DayProvider.calculateOffset(workingDay.day)
                            )
                        }
                    )
                }
                .flatMap { it.actions }
                .zipWithNext()
                .filter {
                    it.first.action == Action.OPEN.input
                }
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
    private val openingTime: Long,
    private val closingTime: Long
)