package com.almeydajuan.openinghours

typealias WorkingWeek = List<WorkingDay>

fun WorkingWeek.areDaysRepeated() = this.map { it.day }.toSet().size != this.size