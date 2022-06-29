package com.example.newsapp.data.repository

import com.example.newsapp.data.ArticleDatabase
import com.example.newsapp.model.Article
import com.example.newsapp.network.ApiClient

class ArticleRepository(private val db: ArticleDatabase) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        ApiClient.getService().getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        ApiClient.getService().searchForNews(searchQuery, pageNumber)

    suspend fun insert(article: Article) = db.favoriteArticleDao().insert(article)

    fun getArticleByTitle(title: String) = db.favoriteArticleDao().getArticleByTitle(title)

    fun getSavedNews() = db.favoriteArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.favoriteArticleDao().deleteArticle(article)
}