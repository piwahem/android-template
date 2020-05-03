package com.example.androidtemplate.domain.contract

import com.example.androidtemplate.domain.entity.ArticleDomainEntity
import com.example.androidtemplate.domain.entity.SourceDomainEntity
import com.example.androidtemplate.helper.Result
import com.example.androidtemplate.helper.Error

interface NewsRemoteContract {
    suspend fun getRemoteArticlesBySource(source: String): Result<List<ArticleDomainEntity>, Error>
    suspend fun getRemoteArticlesBySearching(query: String): Result<List<ArticleDomainEntity>, Error>
    suspend fun getRemoteArticlesBySearching(query: String, page: Int, pageSize: Int): Result<List<ArticleDomainEntity>, Error>
    suspend fun getRemoteSource(): Result<List<SourceDomainEntity>, Error>
}