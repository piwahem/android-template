package com.example.androidtemplate.domain.contract

import com.example.androidtemplate.domain.entity.ArticleDomainEntity
import com.example.androidtemplate.domain.entity.SourceDomainEntity
import com.example.androidtemplate.helper.Result
import com.example.androidtemplate.helper.Error

interface NewsLocalContract {
    suspend fun getLocalSources(): Result<List<SourceDomainEntity>, Error>
    suspend fun getLocalArticlesBySource(source: String): Result<List<ArticleDomainEntity>, Error>
    suspend fun getLocalArticlesBySearching(query: String): Result<List<ArticleDomainEntity>, Error>
    suspend fun saveSources(sources: List<SourceDomainEntity>): Result<List<Long>, Error>
    suspend fun saveArticles(articles: List<ArticleDomainEntity>): Result<List<Long>, Error>
}