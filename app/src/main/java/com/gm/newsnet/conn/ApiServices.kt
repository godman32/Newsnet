package com.gm.newsnet.conn

import com.gm.newsnet.BuildConfig
import com.gm.newsnet.news.Articles
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by @godman on 04/07/23.
 */

interface ApiServices {

    @GET("top-headlines?apiKey="+ BuildConfig.Api_Token)
    suspend fun getTopHeadlines(@Query("category") category: String, @Query("q") search: String, @Query("page") page: Int) : Response<Articles>

}