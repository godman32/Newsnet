package com.gm.newsnet.news

import com.google.gson.annotations.SerializedName

/**
 * Created by @godman on 04/07/23.
 */

 class Article : java.io.Serializable{
    @SerializedName("source"      ) var source      : Source? = Source()
    @SerializedName("author"      ) var author      : String? = null
    @SerializedName("title"       ) var title       : String? = null
    @SerializedName("description" ) var description : String? = null
    @SerializedName("url"         ) var url         : String? = null
    @SerializedName("urlToImage"  ) var urlToImage  : String? = null
    @SerializedName("publishedAt" ) var publishedAt : String? = null
    @SerializedName("content"     ) var content     : String? = null
}