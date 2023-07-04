package com.gm.newsnet.news

import com.google.gson.annotations.SerializedName


/**
 * Created by @godman on 04/07/23.
 */

data class Articles (
    @SerializedName("status"       ) var status       : String?             = null,
    @SerializedName("totalResults" ) var totalResults : Int?                = null,
    @SerializedName("articles"     ) var articles     : ArrayList<Article> = arrayListOf(),

    @SerializedName("code"    ) var code    : String? = null,
    @SerializedName("message" ) var message : String? = null
)