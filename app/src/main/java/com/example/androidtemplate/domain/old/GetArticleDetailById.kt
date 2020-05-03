package com.example.androidtemplate.domain.old

import io.reactivex.Observable

class GetArticleDetailById(private val repository: ArticleContract) : UseCase<ArticleEntity?>() {
    companion object{
        const val KEY_ARTICLE_ID = "KEY_ARTICLE_ID"
    }

    override fun createObservable(data: Map<String, Any>?): Observable<ArticleEntity?> {
        val id = (data?.get(KEY_ARTICLE_ID)?:"") as? String
        id?.let {
            return repository.getArticleDetailById(it)
        } ?: return Observable.fromCallable { null }
    }

    fun getArticleDetailById(id: String): Observable<ArticleEntity?>{
        val data = HashMap<String, String>().apply {
            put(KEY_ARTICLE_ID, id)
        }
        return createObservable(data)
    }
}