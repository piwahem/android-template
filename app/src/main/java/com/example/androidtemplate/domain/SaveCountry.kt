package com.example.androidtemplate.domain

import io.reactivex.Observable

class SaveCountry(private val repository: ArticleContract) : UseCase<Boolean>() {
    companion object{
        const val KEY_COUNTRY = "KEY_COUNTRY"
    }

    override fun createObservable(data: Map<String, Any>?): Observable<Boolean> {
        val category = data?.get(KEY_COUNTRY) as? String
        category?.let {
            return repository.saveCountry(it)
        } ?: return Observable.fromCallable { false }
    }

    fun saveCountry(item: String): Observable<Boolean>{
        val data = HashMap<String, String>().apply {
            put(KEY_COUNTRY, item)
        }
        return createObservable(data)
    }
}