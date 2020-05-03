package com.example.androidtemplate.data.remote

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.androidtemplate.domain.contract.NewsRemoteContract
import com.example.androidtemplate.domain.entity.ArticleDomainEntity
import com.example.androidtemplate.domain.usecase.SearchArticlesPaginationByQuery
import com.example.androidtemplate.helper.LoadingMoreState
import com.example.androidtemplate.helper.Result
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import timber.log.Timber

class NewsArticlePaginationRemoteDataSource(
    private val remoteDataSource: NewsRemoteContract,
    private val query: String
) : PageKeyedDataSource<Int, ArticleDomainEntity>() {

    var loadingMoreState: MutableLiveData<LoadingMoreState> = MutableLiveData()
    private lateinit var retry: () -> Unit

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ArticleDomainEntity>
    ) {
        MainScope().launch {
            setStateLoadingMore(LoadingMoreState.Loading())
            val result = remoteDataSource.getRemoteArticlesBySearching(
                query,
                SearchArticlesPaginationByQuery.DEFAULT_PAGE,
                SearchArticlesPaginationByQuery.DEFAULT_PAGE_SIZE
            )
            when (result) {
                is Result.Success -> {
                    callback.onResult(
                        result.successData,
                        null,
                        SearchArticlesPaginationByQuery.DEFAULT_PAGE + 1
                    )
                }
                is Result.Failure -> {
                    setStateLoadingMore(LoadingMoreState.Error())
                    setRetryAction { loadInitial(params, callback) }
                }
            }
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ArticleDomainEntity>
    ) {
        MainScope().launch {
            setStateLoadingMore(LoadingMoreState.Loading())
            val result = remoteDataSource.getRemoteArticlesBySearching(
                query,
                params.key,
                params.requestedLoadSize
            )
            when (result) {
                is Result.Success -> {
                    callback.onResult(
                        result.successData,
                        params.key + 1
                    )
                    if (result.successData.isEmpty()) {
                        setStateLoadingMore(LoadingMoreState.Done())
                    }
                }
                is Result.Failure -> {
                    setStateLoadingMore(LoadingMoreState.Error())
                    setRetryAction { loadAfter(params, callback) }
                }
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, ArticleDomainEntity>
    ) {

    }

    fun retry() {
        retry.invoke()
    }

    private fun setStateLoadingMore(state: LoadingMoreState) {
        Timber.e("state is $state")
        this.loadingMoreState.postValue(state)
    }

    private fun setRetryAction(action: () -> Unit) {
        this.retry = action
    }
}