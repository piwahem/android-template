package com.example.androidtemplate.data.remote

data class ArticleNetworkResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
){
    data class Article(
        val author: String? = "EMPTY_AUTHOR",
        val content: String,
        val description: String,
        val publishedAt: String,
        val source: Source,
        val title: String,
        val url: String,
        val urlToImage: String? = ""
    )

    data class Source(
        val id: String? = "EMPTY_SOURCE_ID",
        val name: String
    )
}