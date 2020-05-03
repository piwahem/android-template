package com.example.androidtemplate.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.androidtemplate.domain.entity.ArticleDomainEntity
import com.example.androidtemplate.domain.usecase.SearchArticlesPaginationByQuery

class SearchArticlesPaginationFactory(
    private val searchUsecase: SearchArticlesPaginationByQuery,
    private val query: String
): DataSource.Factory<Int, ArticleDomainEntity>() {

    val articleDataSourceLiveData = MutableLiveData<SearchArticlesDataSourcePagination>()

    override fun create(): DataSource<Int, ArticleDomainEntity> {
        val dataSource = SearchArticlesDataSourcePagination(searchUsecase, query)
        articleDataSourceLiveData.postValue(dataSource)
        return dataSource
    }
}