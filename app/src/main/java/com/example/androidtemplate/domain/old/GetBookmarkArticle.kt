package com.example.androidtemplate.domain.old

import io.reactivex.Observable

class GetBookmarkArticle(private val repository: ArticleContract) : UseCase<List<ArticleEntity>?>() {

    override fun createObservable(data: Map<String, Any>?): Observable<List<ArticleEntity>?> {
        return repository.getBookmarkArticle()
    }

    fun getBookmarkArticle(): Observable<List<ArticleEntity>?>{
        val data = HashMap<String, String>()
        return createObservable(data)
    }
}