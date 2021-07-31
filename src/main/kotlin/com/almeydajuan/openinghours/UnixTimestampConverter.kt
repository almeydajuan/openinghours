package com.almeydajuan.openinghours

import java.lang.RuntimeException
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.TimeZone

class UnixTimestampConverter {

    companion object {
        private val hourFormatter = SimpleDateFormat("HH").apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }

        private val minutesFormatter = SimpleDateFormat("mm")

        fun convert(unixTimestamp: Long): String {
            val date = Date.from(Instant.ofEpochSecond(unixTimestamp))
            val hour = hourFormatter.format(date).toInt()
            val minutes = minutesFormatter.format(date).toString().toInt()

            val hourText = if (hour > 12) hour - 12 else hour
            val minutesText = if (minutes > 0) ".$minutes" else ""
            val meridiem = if (hour >= 12) "PM" else "AM"

            return "$hourText$minutesText $meridiem"
        }
    }
}