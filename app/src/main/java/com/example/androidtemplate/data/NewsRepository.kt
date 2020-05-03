package com.example.androidtemplate.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.androidtemplate.data.remote.NewsArticlePaginationRemoteDataSource
import com.example.androidtemplate.data.remote.NewsArticlesPaginationRemoteDataSourceFactory
import com.example.androidtemplate.domain.contract.NewsContract
import com.example.androidtemplate.domain.contract.NewsLocalContract
import com.example.androidtemplate.domain.contract.NewsRemoteContract
import com.example.androidtemplate.domain.entity.ArticleDomainEntity
import com.example.androidtemplate.domain.usecase.SearchArticlesPaginationByQuery
import com.example.androidtemplate.helper.Result
import com.example.androidtemplate.helper.Error


class NewsRepository(
    private val remoteDataStore: NewsRemoteContract,
    private val localDataStore: NewsLocalContract
) : NewsContract {

    override suspend fun getArticlesBySearching(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<ArticleDomainEntity>, Error> {
        return remoteDataStore.getRemoteArticlesBySearching(query, page, pageSize)
    }

    override suspend fun getArticlesBySource(source: String): Result<List<ArticleDomainEntity>, Error> {
        return getArticlesBySource(source, true)
    }

    override suspend fun getArticlesBySource(
        source: String,
        isGetOnline: Boolean
    ): Result<List<ArticleDomainEntity>, Error> {
        source?.let {
            return (if (isGetOnline) {
                when (val remote = remoteDataStore.getRemoteArticlesBySource(it)) {
                    is Result.Success -> {
                        localDataStore.saveArticles(remote.successData)
                        remote
                    }
                    else -> {
                        remote
                    }
                }
            } else {
                localDataStore.getLocalArticlesBySource(it)
            })
        }
    }
}