package com.example.androidtemplate.presentation

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.androidtemplate.domain.entity.ArticleDomainEntity
import com.example.androidtemplate.helper.Error
import com.example.androidtemplate.helper.LoadingMoreState

/**
 * Created by Yossi Segev on 09/02/2018.
 */
data class NewsSearchArticleViewState(
        var articles: PagedList<ArticleDomainEntity>? = null,
        var loadingState: LoadingMoreState? = null
)