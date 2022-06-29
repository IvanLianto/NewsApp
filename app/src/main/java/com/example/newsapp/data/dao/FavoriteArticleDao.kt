package com.example.newsapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsapp.model.Article

@Dao
interface FavoriteArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article): Long

    @Query("SELECT * FROM article")
    fun getAllArticles(): LiveData<List<Article>>

    @Query("SELECT * FROM article WHERE title = :title")
    fun getArticleByTitle(title: String): LiveData<Article>

    @Delete
    suspend fun deleteArticle(article: Article)
}