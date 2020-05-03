package com.example.androidtemplate.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidtemplate.domain.usecase.SearchArticlesPaginationByQuery

/**
 * Created by Yossi Segev on 01/01/2018.
 */
class NewsSearchArticleViewModelFactory(private val useCase: SearchArticlesPaginationByQuery) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsSearchArticleViewModel(useCase) as T
    }
}