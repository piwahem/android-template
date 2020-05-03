package com.example.androidtemplate.domain.old

import io.reactivex.Observable

class SearchArticle(private val repository: ArticleContract) : UseCase<List<ArticleEntity>?>() {
    companion object{
        const val KEY_QUERY = "KEY_QUERY"
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<ArticleEntity>?> {
        val query = data?.get(KEY_QUERY) as? String
        query?.let {
            return repository.searchArticle(it)
        } ?: return Observable.fromCallable { null }
    }

    fun searchArticle(query: String): Observable<List<ArticleEntity>?>{
        val data = HashMap<String, String>().apply {
            put(KEY_QUERY, query)
        }
        return createObservable(data)
    }
}