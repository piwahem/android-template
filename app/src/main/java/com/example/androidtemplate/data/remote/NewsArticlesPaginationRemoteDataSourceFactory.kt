package com.example.androidtemplate.data.remote

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.androidtemplate.domain.contract.NewsRemoteContract
import com.example.androidtemplate.domain.entity.ArticleDomainEntity

class NewsArticlesPaginationRemoteDataSourceFactory(
    private val remoteDataSource: NewsRemoteContract,
    private val query: String
) : DataSource.Factory<Int, ArticleDomainEntity>() {

    val articleDataSourceLiveData = MutableLiveData<NewsArticlePaginationRemoteDataSource>()

    override fun create(): DataSource<Int, ArticleDomainEntity> {
        val dataSource = NewsArticlePaginationRemoteDataSource(remoteDataSource, query)
        articleDataSourceLiveData.postValue(dataSource)
        return dataSource
    }
}