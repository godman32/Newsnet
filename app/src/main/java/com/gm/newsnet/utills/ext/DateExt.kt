package com.gm.newsnet.utills.ext

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created by @godman on 04/07/23.
 */

@SuppressLint("SimpleDateFormat")
fun Date.format(format: String):String{
    val df = SimpleDateFormat(format)

    return df.format(this)
}