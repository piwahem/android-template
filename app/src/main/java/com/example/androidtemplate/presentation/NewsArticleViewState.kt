package com.example.androidtemplate.presentation

import com.example.androidtemplate.domain.entity.ArticleDomainEntity
import com.example.androidtemplate.helper.Error

/**
 * Created by Yossi Segev on 09/02/2018.
 */
data class NewsArticleViewState(
        val isLoading: Boolean = false,
        val articles: List<ArticleDomainEntity>? = null,
        val error: Error? = null
)