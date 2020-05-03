package com.example.androidtemplate.domain.old

import io.reactivex.Observable

interface ArticleContract {
    fun getArticle(source: String): Observable<List<ArticleEntity>?>
    fun getArticleDetailById(id: String): Observable<ArticleEntity?>
    fun getSource(category: String?, language: String?, country: String?): Observable<List<SourceEntity>?>
    fun getBookmarkArticle(): Observable<List<ArticleEntity>?>
    fun searchArticle(query: String): Observable<List<ArticleEntity>?>
    fun bookmarkArticle(item: ArticleEntity): Observable<Boolean>
    fun saveSource(item: SourceEntity): Observable<Boolean>
    fun saveCategory(value: String): Observable<Boolean>
    fun saveLanguage(value: String): Observable<Boolean>
    fun saveCountry(value: String): Observable<Boolean>
}