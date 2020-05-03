package com.example.androidtemplate.data.local

import androidx.room.*

@Dao
interface SourceLocalDAO {
    @Query("SELECT * FROM source")
    fun getSources(): List<SourceLocalDTO>

    @Query("SELECT * FROM source WHERE id=:id")
    fun getSourceById(id: String): SourceLocalDTO

    @Query("SELECT * FROM source WHERE category=:category AND language=:language AND country=:country")
    fun getSourceBy(
        category: String,
        language: String,
        country: String
        ): List<SourceLocalDTO>


    @Query("DELETE FROM source WHERE id=:id")
    fun deleteSourceById(id: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSources(sources: List<SourceLocalDTO>): List<Long>

    @Transaction
    @Query("SELECT * FROM source")
    fun getSourcesWithArticles(): List<SourceWithArticleLocalDTO>
}