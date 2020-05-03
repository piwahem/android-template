package com.example.androidtemplate.presentation

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.androidtemplate.data.*
import com.example.androidtemplate.domain.entity.ArticleDomainEntity
import com.example.androidtemplate.domain.usecase.SearchArticlesPaginationByQuery
import com.example.androidtemplate.domain.usecase.SearchArticlesPaginationByQuery.Companion.DEFAULT_PAGE_SIZE
import kotlinx.coroutines.*
import timber.log.Timber

class NewsSearchArticleViewModel(
    private var searchArticle: SearchArticlesPaginationByQuery
) : ViewModel() {

    private var job = MainScope().launch {  }
    private lateinit var searchArticlesPaginationFactory: SearchArticlesPaginationFactory

    private val config = PagedList.Config.Builder()
        .setPageSize(DEFAULT_PAGE_SIZE)
        .setInitialLoadSizeHint(DEFAULT_PAGE_SIZE / 2)
        .setEnablePlaceholders(false)
        .build()

    var viewState: MutableLiveData<NewsSearchArticleViewState> = MutableLiveData()


    init {
        val viewState = NewsSearchArticleViewState()
        this.viewState.value = viewState
    }

    fun search(query: String, owner: LifecycleOwner) {
        cancel()
        job = MainScope().launch {
            delay(500)
            Timber.e("query = $query")
            searchArticlesPaginationFactory  = SearchArticlesPaginationFactory(searchArticle, query)
            val articles = LivePagedListBuilder(searchArticlesPaginationFactory, config).build()
            articles?.observe(owner, Observer<PagedList<ArticleDomainEntity>> {
                viewState?.value = viewState?.value?.copy(
                    articles = it
                )
            })
            val state = Transformations.switchMap(
                searchArticlesPaginationFactory.articleDataSourceLiveData,
                SearchArticlesDataSourcePagination::loadingMoreState
            )
            state?.observe(owner, Observer {
                viewState?.value = viewState?.value?.copy(
                    loadingState = it
                )
            })
        }
    }

    fun retry(){
        searchArticlesPaginationFactory.articleDataSourceLiveData.value?.retry()
    }

    override fun onCleared() {
        super.onCleared()
        cancel()
    }

    private fun cancel(){
        job.cancel()
    }

    companion object {
        const val DEFAULT_SOURCE_ID = "abc-news"
    }
}