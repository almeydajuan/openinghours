package com.almeydajuan.openinghours.parser

import com.almeydajuan.openinghours.OpeningTime

data class OpeningTimeParser(
    private val timeParser: UnixTimestampParser
) {

    fun parseOpeningTime(openingTime: OpeningTime): String {
        val text = StringBuilder()
        text.append(timeParser.convert(openingTime.openingTime))
        text.append(" - ")
        text.append(timeParser.convert(openingTime.closingTime))
        return text.toString()
    }
}