package com.gm.newsnet.utills.ext

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by @godman on 04/07/23.
 */

fun String.toDate(format:String):Date{
    val inputFormatter: DateFormat = SimpleDateFormat(format)
    inputFormatter.setTimeZone(TimeZone.getTimeZone("UTC"))

    return inputFormatter.parse(this) as Date
}

fun String.truncateExtra():String{
    return this.replace("(\\[\\+\\d+ chars])".toRegex(), "")
}

