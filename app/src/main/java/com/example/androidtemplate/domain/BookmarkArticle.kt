package com.example.androidtemplate.domain

import io.reactivex.Observable

class BookmarkArticle(private val repository: ArticleContract) : UseCase<Boolean>() {
    companion object{
        const val KEY_ARTICLE = "KEY_ARTICLE"
    }

    override fun createObservable(data: Map<String, Any>?): Observable<Boolean> {
        val article = (data?.get(KEY_ARTICLE)?:null) as? ArticleEntity
        article?.let {
            return repository.bookmarkArticle(it)
        } ?: return Observable.fromCallable { false }
    }

    fun bookmarkArticle(item: ArticleEntity): Observable<Boolean>{
        val data = HashMap<String, ArticleEntity>().apply {
            put(KEY_ARTICLE, item)
        }
        return createObservable(data)
    }
}