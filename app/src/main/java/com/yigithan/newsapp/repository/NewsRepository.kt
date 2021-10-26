package com.yigithan.newsapp.repository

import com.yigithan.newsapp.api.RetrofitInstance
import com.yigithan.newsapp.db.ArticleDb
import com.yigithan.newsapp.models.Article

class NewsRepository(val db:ArticleDb) {

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchNews(searchQuery,pageNumber)

    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)
}