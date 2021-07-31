package com.almeydajuan.openinghours.parser

import com.almeydajuan.openinghours.OpeningTime
import com.almeydajuan.openinghours.UnixTimestampConverter
import java.lang.StringBuilder

class OpeningTimeParser {

    companion object {
        fun parseOpeningTime(openingTime: OpeningTime): String {
            val text = StringBuilder()
            text.append(UnixTimestampConverter.convert(openingTime.openingTime))
            text.append(" - ")
            text.append(UnixTimestampConverter.convert(openingTime.closingTime))
            return text.toString()
        }
    }
}