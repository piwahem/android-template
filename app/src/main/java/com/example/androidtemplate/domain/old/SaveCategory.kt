package com.example.androidtemplate.domain.old

import io.reactivex.Observable

class SaveCategory(private val repository: ArticleContract) : UseCase<Boolean>() {
    companion object{
        const val KEY_CATEGORY = "KEY_CATEGORY"
    }

    override fun createObservable(data: Map<String, Any>?): Observable<Boolean> {
        val category = data?.get(KEY_CATEGORY) as? String
        category?.let {
            return repository.saveCategory(it)
        } ?: return Observable.fromCallable { false }
    }

    fun saveCategory(item: String): Observable<Boolean>{
        val data = HashMap<String, String>().apply {
            put(KEY_CATEGORY, item)
        }
        return createObservable(data)
    }
}