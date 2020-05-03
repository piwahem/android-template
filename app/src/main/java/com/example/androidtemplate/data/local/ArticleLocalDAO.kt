package com.example.androidtemplate.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticleLocalDAO {
    @Query("SELECT * FROM article WHERE sourceId = :source")
    fun getArticlesBySource(source: String): List<ArticleLocalDTO>

    @Query("SELECT * FROM article WHERE url=:url")
    fun getArticleByUrl(url: String): ArticleLocalDTO

    @Query("DELETE FROM article WHERE url=:url")
    fun deleteArticleByUrl(url: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveArticles(articles: List<ArticleLocalDTO>): List<Long>

    @Query("SELECT * FROM article WHERE title LIKE '%'||:query||'%' OR author LIKE '%'||:query||'%' ")
    fun getArticleBySearching(query: String): List<ArticleLocalDTO>
}