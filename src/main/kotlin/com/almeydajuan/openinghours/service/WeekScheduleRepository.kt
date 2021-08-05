package com.almeydajuan.openinghours.service

import com.almeydajuan.openinghours.OpeningTime
import java.util.UUID

class WeekScheduleRepository {

    private val schedules: MutableMap<String, List<OpeningTime>> = mutableMapOf()

    fun saveWeekSchedule(openingTimes: List<OpeningTime>) = schedules.put(UUID.randomUUID().toString(), openingTimes)
}