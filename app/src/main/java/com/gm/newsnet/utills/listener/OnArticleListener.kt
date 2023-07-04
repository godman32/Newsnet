package com.gm.newsnet.utills.listener

import com.gm.newsnet.news.Article

/**
 * Created by @godman on 04/07/23.
 */

interface OnArticleListener {
    fun onArticleSelected(data: Article) {}
}