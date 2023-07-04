package com.gm.newsnet.news

import com.google.gson.annotations.SerializedName


class Source : java.io.Serializable{
    @SerializedName("id"   ) var id   : String? = null
    @SerializedName("name" ) var name : String? = null

}