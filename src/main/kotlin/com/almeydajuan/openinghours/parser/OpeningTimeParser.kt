package com.almeydajuan.openinghours.parser

import com.almeydajuan.openinghours.OpeningTime
import com.almeydajuan.openinghours.UnixTimestampConverter

data class OpeningTimeParser(
    private val timeConverter: UnixTimestampConverter
) {

    fun parseOpeningTime(openingTime: OpeningTime): String {
        val text = StringBuilder()
        text.append(timeConverter.convert(openingTime.openingTime))
        text.append(" - ")
        text.append(timeConverter.convert(openingTime.closingTime))
        return text.toString()
    }
}