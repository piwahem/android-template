package com.example.androidtemplate.data.local

import com.example.androidtemplate.data.ArticleMapper
import com.example.androidtemplate.data.SourceMapper
import com.example.androidtemplate.domain.contract.NewsLocalContract
import com.example.androidtemplate.domain.entity.ArticleDomainEntity
import com.example.androidtemplate.domain.entity.SourceDomainEntity
import com.example.androidtemplate.helper.Result
import com.example.androidtemplate.helper.Error
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsLocalDataSource(
    private val db: NewsDatabase,
    private val articleMapper: ArticleMapper,
    private val sourceMapper: SourceMapper
) : NewsLocalContract {

    override suspend fun getLocalSources(): Result<List<SourceDomainEntity>, Error> {
        return withContext(Dispatchers.IO) {
            return@withContext Result.Success(
                sourceMapper.mapLocalToEntities(
                    db.getSourceDAO().getSources()
                )
            )
        }
    }

    override suspend fun getLocalArticlesBySource(source: String): Result<List<ArticleDomainEntity>, Error> {
        return withContext(Dispatchers.IO) {
            val result = db.getArticleDAO().getArticlesBySource(source)
            return@withContext Result.Success(articleMapper.mapLocalToEntities(result))
        }
    }

    override suspend fun getLocalArticlesBySearching(query: String): Result<List<ArticleDomainEntity>, Error> {
        return Result.Success(
            articleMapper.mapLocalToEntities(
                db.getArticleDAO().getArticleBySearching(query)
            )
        )
    }

    override suspend fun saveSources(sources: List<SourceDomainEntity>): Result<List<Long>, Error> {
        return withContext(Dispatchers.IO) {
            val input = sourceMapper.mapEntitiesToLocal(sources)
            val index = db.getSourceDAO().addSources(input)
            return@withContext Result.Success(index)
        }
    }

    override suspend fun saveArticles(articles: List<ArticleDomainEntity>): Result<List<Long>, Error> {
        return withContext(Dispatchers.IO) {
            val input = articleMapper.mapEntitiesToLocal(articles)
            val index = db.getArticleDAO().saveArticles(input)
            return@withContext Result.Success(index)
        }
    }

    private fun getDb(): NewsDatabase {
        return NewsDatabase.getInstance(Any())
    }
}