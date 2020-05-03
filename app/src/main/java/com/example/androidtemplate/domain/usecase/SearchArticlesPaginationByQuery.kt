package com.example.androidtemplate.domain.usecase

import com.example.androidtemplate.domain.contract.NewsContract
import com.example.androidtemplate.domain.entity.ArticleDomainEntity
import com.example.androidtemplate.helper.Error
import com.example.androidtemplate.helper.Result
import timber.log.Timber

class SearchArticlesPaginationByQuery(
    private val repository: NewsContract
) : IBaseUseCase<List<ArticleDomainEntity>> {

    override suspend fun create(data: Map<String, Any>?): Result<List<ArticleDomainEntity>, Error> {
        val query = (data?.get(KEY_QUERY) ?: "") as String
        val page = (data?.get(KEY_PAGE) ?: DEFAULT_PAGE) as Int
        val pageSize = (data?.get(KEY_PAGE_SIZE) ?: DEFAULT_PAGE_SIZE) as Int
        Timber.e("query = $query, page = $page, pageSize = $pageSize")
        return repository.getArticlesBySearching(query, page, pageSize)
    }

    suspend fun searchArticles(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<ArticleDomainEntity>, Error> {
        val data = HashMap<String, Any>()
        data[KEY_QUERY] = query
        data[KEY_PAGE] = page
        data[KEY_PAGE_SIZE] = pageSize
        return create(data)
    }

    companion object {
        const val KEY_QUERY = "KEY_QUERY"
        const val KEY_PAGE_SIZE = "KEY_PAGE_SIZE"
        const val KEY_PAGE = "KEY_PAGE"

        const val DEFAULT_PAGE_SIZE = 5
        const val DEFAULT_PAGE = 1
    }
}