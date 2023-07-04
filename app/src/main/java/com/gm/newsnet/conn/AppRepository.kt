package com.gm.newsnet.conn


/**
 * Created by @godman on 04/07/23.
 */

class AppRepository() {

    suspend fun getTopHeadlines(cat:String, search:String, page:Int) = NetworkBuilder.api.getTopHeadlines(cat, search, page)

}