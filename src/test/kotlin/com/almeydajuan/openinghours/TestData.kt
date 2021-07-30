package com.almeydajuan.openinghours

val nineToEleven = listOf(
    DayAction(Action.OPEN.input, NINE_AM_UNIX),
    DayAction(Action.CLOSE.input, ELEVEN_AM_UNIX)
)

val typicalMonday = WorkingDay(
    day = Day.MONDAY.input,
    actions = nineToEleven
)