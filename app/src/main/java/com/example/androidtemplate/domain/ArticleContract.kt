package com.example.androidtemplate.domain

import io.reactivex.Observable

interface ArticleContract {
    fun getArticle(source: String): Observable<List<ArticleEntity>?>
    fun getSource(category: String, language: String, country: String): Observable<List<SourceEntity>?>
    fun searchArticle(query: String): Observable<List<ArticleEntity>?>
}