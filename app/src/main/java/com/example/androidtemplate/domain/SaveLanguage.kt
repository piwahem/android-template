package com.example.androidtemplate.domain

import io.reactivex.Observable

class SaveLanguage(private val repository: ArticleContract) : UseCase<Boolean>() {
    companion object{
        const val KEY_LANGUAGE = "KEY_LANGUAGE"
    }

    override fun createObservable(data: Map<String, Any>?): Observable<Boolean> {
        val category = data?.get(KEY_LANGUAGE) as? String
        category?.let {
            return repository.saveLanguage(it)
        } ?: return Observable.fromCallable { false }
    }

    fun saveCountry(item: String): Observable<Boolean>{
        val data = HashMap<String, String>().apply {
            put(KEY_LANGUAGE, item)
        }
        return createObservable(data)
    }
}