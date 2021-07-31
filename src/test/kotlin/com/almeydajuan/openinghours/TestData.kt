package com.almeydajuan.openinghours

import com.almeydajuan.openinghours.provider.Action
import com.almeydajuan.openinghours.provider.Action.CLOSE
import com.almeydajuan.openinghours.provider.Action.OPEN
import com.almeydajuan.openinghours.provider.Day
import com.almeydajuan.openinghours.provider.Day.MONDAY

const val ONE_AM_UNIX: Long = 3600
const val NINE_AM_UNIX: Long = 32400
const val ELEVEN_AM_UNIX: Long = 39600
const val ONE_PM_UNIX: Long = 46800
const val SIX_PM_UNIX: Long = 64800

val openingNineToEleven = OpeningTime(NINE_AM_UNIX, ELEVEN_AM_UNIX)
val openingOneToSix = OpeningTime(ONE_PM_UNIX, SIX_PM_UNIX)

val nineToEleven = listOf(
    Transition(OPEN.input, NINE_AM_UNIX),
    Transition(CLOSE.input, ELEVEN_AM_UNIX)
)

val oneToSix = listOf(
    Transition(OPEN.input, ONE_PM_UNIX),
    Transition(CLOSE.input, SIX_PM_UNIX)
)

val typicalMonday = WorkingDay(
    day = MONDAY.input,
    transitions = nineToEleven
)