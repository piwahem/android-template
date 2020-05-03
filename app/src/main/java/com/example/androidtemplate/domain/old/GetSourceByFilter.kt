package com.example.androidtemplate.domain.old

import io.reactivex.Observable

class GetSourceByFilter(private val repository: ArticleContract) : UseCase<List<SourceEntity>?>() {
    companion object{
        const val KEY_LANGUAGE = "KEY_LANGUAGE"
        const val KEY_CATEGORY = "KEY_CATEGORY"
        const val KEY_COUNTRY = "KEY_COUNTRY"
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<SourceEntity>?> {
        val category = data?.get(KEY_CATEGORY) as? String
        val language = data?.get(KEY_LANGUAGE) as? String
        val country = data?.get(KEY_COUNTRY) as? String
        return repository.getSource(category, language, country)
    }

    fun getSourceByFilter(category: String, language: String, country: String): Observable<List<SourceEntity>?>{
        val data = HashMap<String, String>().apply {
            put(KEY_CATEGORY, category)
            put(KEY_LANGUAGE, language)
            put(KEY_COUNTRY, country)
        }
        return createObservable(data)
    }
}