package com.example.androidtemplate.domain

import io.reactivex.Observable

class SaveSource(private val repository: ArticleContract) : UseCase<Boolean>() {
    companion object{
        const val KEY_SOURCE = "KEY_SOURCE"
    }

    override fun createObservable(data: Map<String, Any>?): Observable<Boolean> {
        val source = data?.get(KEY_SOURCE) as? SourceEntity
        source?.let {
            return repository.saveSource(it)
        } ?: return Observable.fromCallable { false }
    }

    fun saveSource(item: SourceEntity): Observable<Boolean>{
        val data = HashMap<String, SourceEntity>().apply {
            put(KEY_SOURCE, item)
        }
        return createObservable(data)
    }
}