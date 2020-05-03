package com.example.androidtemplate.domain.usecase

import com.example.androidtemplate.domain.contract.NewsContract
import com.example.androidtemplate.domain.entity.ArticleDomainEntity
import com.example.androidtemplate.helper.Error
import com.example.androidtemplate.helper.Result

open class GetArticlesBySource(
    private val repository: NewsContract
) : IBaseUseCase<List<ArticleDomainEntity>> {

    override suspend fun create(data: Map<String, Any>?): Result<List<ArticleDomainEntity>, Error> {
        val source = (data?.get(KEY_SOURCE) ?: "") as String
        val isGetOnline = (data?.get(KEY_GET_ONLINE) ?: false) as Boolean
        return repository.getArticlesBySource(source, isGetOnline)
    }

    suspend fun getLocalArticle(source: String): Result<List<ArticleDomainEntity>, Error> {
        val data = HashMap<String, Any>()
        data[KEY_SOURCE] = source
        data[KEY_GET_ONLINE] = false
        return create(data)
    }

    suspend fun getRemoteArticle(source: String): Result<List<ArticleDomainEntity>, Error> {
        val data = HashMap<String, Any>()
        data[KEY_SOURCE] = source
        data[KEY_GET_ONLINE] = true
        return create(data)
    }

    companion object {
        const val KEY_SOURCE = "KEY_SOURCE"
        const val KEY_GET_ONLINE = "KEY_GET_ONLINE"
    }
}