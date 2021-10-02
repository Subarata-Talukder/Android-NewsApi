package com.softlabio.newsapi.repository

import com.softlabio.newsapi.api.RetrofitInstance
import com.softlabio.newsapi.db.NewsDb
import com.softlabio.newsapi.model.Article

class NewsRepository(
    val db: NewsDb
) {
    suspend fun getArticle(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBrakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchNews(searchQuery, pageNumber)

    suspend fun upsert(article: Article) = db.articleDao().upsert(article)

    fun getArticles() = db.articleDao().getArticles()

    suspend fun deleteArticle(article: Article) = db.articleDao().deleteArticle(article)
}