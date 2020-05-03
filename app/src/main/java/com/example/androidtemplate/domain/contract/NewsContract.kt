package com.example.androidtemplate.domain.contract

import com.example.androidtemplate.domain.entity.ArticleDomainEntity
import com.example.androidtemplate.domain.entity.SourceDomainEntity
import com.example.androidtemplate.helper.Error
import com.example.androidtemplate.helper.Result

interface NewsContract{

    suspend fun getArticlesBySearching(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<ArticleDomainEntity>, Error>

    suspend fun getArticlesBySource(source: String): Result<List<ArticleDomainEntity>, Error>
    suspend fun getArticlesBySource(source: String, isGetOnline: Boolean): Result<List<ArticleDomainEntity>, Error>
}