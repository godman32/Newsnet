package com.gm.newsnet.utills.constant

/**
 * Created by @godman on 04/07/23.
 */

class TimeFormat{
    companion object{
        val SYSTEM= "yyyy-MM-dd'T'HH:mm:ss'Z'"
        val DD_MM_YY= "dd MMM yyyy"
        val DD_MMM_YYYY= "dd MMMM yyyy"
        val DD_MMM_YY_HH_II= "dd MMMM yyyy"

        val MMM_DD_YYYY= "MMM dd, yyyy"
        val EEE_DD_MMM_HH_MM= "EEE, dd MMM HH.mm"

        val MMM_DD_YYYY_HH_MM= "MMM dd, yyyy  HH:mm"
    }
}

enum class Status {
    SUCCESS,
    LOADING,
    IDLE,
    LOAD,
    FREE,
    CLEAR,
    ERROR
}

