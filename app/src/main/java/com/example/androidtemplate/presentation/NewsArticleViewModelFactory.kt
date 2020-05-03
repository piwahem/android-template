package com.example.androidtemplate.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidtemplate.domain.usecase.GetArticlesBySource

/**
 * Created by Yossi Segev on 01/01/2018.
 */
class NewsArticleViewModelFactory(private val useCase: GetArticlesBySource) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsArticleViewModel(useCase) as T
    }
}