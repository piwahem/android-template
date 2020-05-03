package com.example.androidtemplate.data.remote

import com.example.androidtemplate.data.ArticleMapper
import com.example.androidtemplate.data.SourceMapper
import com.example.androidtemplate.domain.contract.NewsRemoteContract
import com.example.androidtemplate.domain.entity.ArticleDomainEntity
import com.example.androidtemplate.domain.entity.SourceDomainEntity
import com.example.androidtemplate.extension.executeRequest
import com.example.androidtemplate.helper.Error
import com.example.androidtemplate.helper.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.Exception

class NewsRemoteDataSource(
    private val api: ApiInterface,
    private val articleMapper: ArticleMapper,
    private val sourceMapper: SourceMapper
) : NewsRemoteContract {

    override suspend fun getRemoteArticlesBySource(source: String): Result<List<ArticleDomainEntity>, Error> {
        return withContext(Dispatchers.IO) {
            return@withContext when (val result = executeRequest { api.getArticles(source) }){
                is Result.Success -> {
                     Result.Success(articleMapper.mapNetworkToEntities(result.successData))
                }
                is Result.Failure -> {
                     result
                }
            }
        }
    }

    override suspend fun getRemoteArticlesBySearching(query: String): Result<List<ArticleDomainEntity>, Error> {
        return withContext(Dispatchers.IO) {
            val response = api.searchArticles(query)
            if (response.isSuccessful){
                var result = emptyList<ArticleDomainEntity>()
                response?.body()?.let {
                    result = articleMapper.mapNetworkToEntities(it)
                }
                return@withContext Result.Success(result)
            }else{
                var error = response?.errorBody()?.string()
                return@withContext Result.Failure(Error.RandomError(Throwable(error)))
            }

        }
    }

    override suspend fun getRemoteArticlesBySearching(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<ArticleDomainEntity>, Error> {
        return withContext(Dispatchers.IO) {
            return@withContext when (val result = executeRequest { api.searchArticlesPagination(query,page, pageSize) }){
                is Result.Success -> {
                    Result.Success(articleMapper.mapNetworkToEntities(result.successData))
                }
                is Result.Failure -> {
                    result
                }
            }
        }
    }

    override suspend fun getRemoteSource(): Result<List<SourceDomainEntity>, Error> {
        return withContext(Dispatchers.IO) {
            return@withContext when (val result = executeRequest { api.getSources("","","") }){
                is Result.Success -> {
                    Result.Success(sourceMapper.mapNetworkToEntities(result.successData))
                }
                is Result.Failure -> {
                    result
                }
            }
        }
    }
}