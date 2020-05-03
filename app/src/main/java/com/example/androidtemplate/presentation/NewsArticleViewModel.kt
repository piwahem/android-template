package com.example.androidtemplate.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidtemplate.data.SearchArticlesDataSourcePagination
import com.example.androidtemplate.domain.usecase.GetArticlesBySource
import com.example.androidtemplate.helper.Result
import kotlinx.coroutines.*
import timber.log.Timber
import java.util.*

class NewsArticleViewModel(
    private var getArticlesBySource: GetArticlesBySource
) : ViewModel() {

    private lateinit var job: Job
    var viewState: MutableLiveData<NewsArticleViewState> = MutableLiveData()

    init {
        val viewState = NewsArticleViewState()
        this.viewState.value = viewState
    }

    fun getArticles() {
        job = MainScope().launch {
            val local = getArticlesBySource.getLocalArticle(DEFAULT_SOURCE_ID)
            viewState.value = viewState.value?.copy(
                isLoading = local.success()?.isNullOrEmpty() ?: true,
                articles = local.success().orEmpty(),
                error = null
            )

            val remote = getArticlesBySource.getRemoteArticle(DEFAULT_SOURCE_ID)
            viewState.value = viewState.value?.copy(
                isLoading = local.success()?.isNullOrEmpty() ?: true,
                articles = getArticlesBySource.getLocalArticle(DEFAULT_SOURCE_ID).success()?.orEmpty(),
                error = when (remote) {
                    is Result.Success -> null
                    is Result.Failure -> remote.errorData
                }
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    companion object {
        const val DEFAULT_SOURCE_ID = "abc-news"
    }
}