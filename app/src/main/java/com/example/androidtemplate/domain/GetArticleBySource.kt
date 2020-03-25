package com.example.androidtemplate.domain

import io.reactivex.Observable

class GetArticleBySource(private val repository: ArticleContract) : UseCase<List<ArticleEntity>?>() {
    companion object{
        const val KEY_SOURCE = "KEY_SOURCE"
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<ArticleEntity>?> {
        val source = (data?.get(KEY_SOURCE)?:"") as? String
        source?.let {
            return repository.getArticle(it)
        } ?: return Observable.fromCallable { null }
    }

    fun getArticleBySource(source: String): Observable<List<ArticleEntity>?>{
        val data = HashMap<String, String>().apply {
            put(KEY_SOURCE, source)
        }
        return createObservable(data)
    }
}