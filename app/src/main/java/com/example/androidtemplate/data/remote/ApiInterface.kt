package com.example.androidtemplate.data.remote

import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("v2/top-headlines")
    suspend fun getArticles(@Query("sources") sources: String): Response<ArticleNetworkResponse>

    @GET("v2/sources") ///movie/now_playing
    suspend fun getSources(
        @Query("category") category: String,
        @Query("language") language: String,
        @Query("country") country: String
    ): Response<SourceNetworkResponse>

    @GET("v2/everything")
    fun searchArticles(@Query("q") q: String): Response<ArticleNetworkResponse>

    @GET("v2/everything")
    suspend fun searchArticlesPagination(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Response<ArticleNetworkResponse>
}