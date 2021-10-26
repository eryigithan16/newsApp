package com.yigithan.newsapp.models

import com.yigithan.newsapp.models.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)